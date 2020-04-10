package com.itmsg.episode.app.system.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.itmsg.episode.app.system.esserver.ESServer;
import com.itmsg.episode.app.system.esserver.ESServerRepository;
import com.itmsg.episode.config.AsyncConfig;
import com.itmsg.episode.scheduler.TaskScheduler;
import com.itmsg.episode.security.JwtUserDetailsService;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESScheduler;

@Service
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JwtUserDetailsService jwtUserDetailsService;

    @Resource
    PasswordEncoder passwordEncoderBean;

    @Resource
    TextEncryptor textEncryptor;

    @Resource
    ESServerRepository esServerRepository;

    @Resource
    private SchedulerMapper schedulerMapper;

    @Resource
    private SchedulerRepository schedulerRepository;

    @Resource
    private SchedulerActionRepository schedulerActionRepository;

    @Resource
    private ApplicationContext applicationContext;

    @Async(AsyncConfig.SCHEDULER_TASK_EXECUTOR)
    public void execute(Scheduler scheduler, String username){
        logger.debug("SchedulerService execute {} : {} ",scheduler.getSchedulerId(),username);
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESScheduler.class);
        TaskScheduler taskScheduler = (TaskScheduler)beans.get(scheduler.getSchedulerId());  
        taskScheduler.setParameter(scheduler.getParameter());
        taskScheduler.execute();
        SchedulerAction schedulerAction = new SchedulerAction();
        schedulerAction.setSchedulerId(scheduler.getSchedulerId());
        schedulerAction.setAction("EXECUTE");
        schedulerAction.setCreateId(username);
        schedulerActionRepository.save(schedulerAction);        
    }

    public void action(Long id,String actionName, String action, HttpServletRequest request){
        Optional<Scheduler> find = schedulerRepository.findById(id);
        if(find.isPresent()){
            Scheduler scheduler = find.get();
            List<ESServer> esServers = esServerRepository.findAll();
            for(ESServer esServer:esServers){
                serverAction(esServer, scheduler, action, request);
            }
            UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
            SchedulerAction schedulerAction = new SchedulerAction();
            schedulerAction.setSchedulerId(scheduler.getSchedulerId());
            schedulerAction.setAction(actionName);
            schedulerAction.setCreateId(userDetails.getUsername());
            schedulerActionRepository.save(schedulerAction);
        }
    }
    
    public void startScheduler(Long id, String username, String password){
        logger.debug("SchedulerService startScheduler {} : {} ",id,username);
        UserDetails userDetails = (UserDetails)jwtUserDetailsService.loadUserByUsername(username);
        if(!userDetails.getPassword().equals(password)){
            throw new BadCredentialsException("Bad credentials !!!!");
        }
        
        Optional<Scheduler> find = schedulerRepository.findById(id);
        if(find.isPresent()){
            Scheduler scheduler = find.get();
            Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESScheduler.class);
            TaskScheduler taskScheduler = (TaskScheduler)beans.get(scheduler.getSchedulerId());  
    
            if("ACTIVE".equals(scheduler.getStatus())){
                taskScheduler.setParameter(scheduler.getParameter());
                taskScheduler.setTrigger(scheduler.getExpression());
                taskScheduler.startScheduler();
            }
        }
      
    }

    public void stopScheduler(Long id, String username, String password){
        logger.debug("SchedulerService startScheduler {} : {} ",id,username);
        UserDetails userDetails = (UserDetails)jwtUserDetailsService.loadUserByUsername(username);
        if(!userDetails.getPassword().equals(password)){
            throw new BadCredentialsException("Bad credentials !!!!");
        }
        Optional<Scheduler> find = schedulerRepository.findById(id);
        if(find.isPresent()){
            Scheduler scheduler = find.get();
            Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESScheduler.class);
            TaskScheduler taskScheduler = (TaskScheduler)beans.get(scheduler.getSchedulerId());  
            taskScheduler.stopScheduler();
        }
    }

    public Boolean isAlive(Scheduler scheduler){
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESScheduler.class);
        TaskScheduler taskScheduler = (TaskScheduler)beans.get(scheduler.getSchedulerId());  
        return taskScheduler.getIsAlive();
    }
    
    public List<Map<String,String>> appSchedulers(){
        List<Map<String,String>>  appSchedulers = new ArrayList<Map<String,String>>();
        
        Map<String,String> scheduler = null;
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESScheduler.class);
        Iterator<String> keys = beans.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            Class clazz = beans.get(key).getClass();
            ESScheduler esScheduler =(ESScheduler)clazz.getAnnotation(ESScheduler.class);
            
            scheduler = new HashMap<String,String>();
            scheduler.put("schedulerId", key);
            scheduler.put("description", esScheduler.description());
            
            appSchedulers.add(scheduler);

        } 
      
        return appSchedulers;
    }

    private void serverAction(ESServer esServer, Scheduler scheduler, String action, HttpServletRequest request) {
        HttpURLConnection con = null;
        try{
            UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
            String address = "http://" + esServer.getIpAddress()+":" + esServer.getPort() + "/episode/api/system/scheduler/" + scheduler.getId() + "/" + action;
            logger.debug("{} {} {}", address, scheduler.getSchedulerId(), action);
            URL url = new URL(address);
            con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("username", userDetails.getUsername());
            con.addRequestProperty("password", userDetails.getPassword());
            con.setConnectTimeout(500);
            con.setReadTimeout(500);
            con.setRequestMethod("POST");
            con.setDoOutput(false);
            con.connect();
            logger.debug("response {}",con.getResponseCode());
        } catch (Exception e) {
            logger.debug("can not {} {}",action ,esServer.getIpAddress()+":" + esServer.getPort());
        }finally{
            if(con!=null){
                con.disconnect();
            }
        }

    }

}
