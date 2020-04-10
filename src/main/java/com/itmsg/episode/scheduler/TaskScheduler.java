package com.itmsg.episode.scheduler;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.itmsg.episode.app.system.esserver.ESServer;
import com.itmsg.episode.app.system.esserver.ESServerRepository;
import com.itmsg.episode.app.system.scheduler.SchedulerHistory;
import com.itmsg.episode.app.system.scheduler.SchedulerHistoryRepository;
import com.itmsg.episode.app.system.scheduler.SchedulerMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public abstract class TaskScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    ESServerRepository esServerRepository;

    @Resource
    SchedulerMapper schedulerMapper;

    @Resource
    SchedulerHistoryRepository schedulerHistoryRepository;

    private Boolean isAlive = false;

    private ThreadPoolTaskScheduler scheduler;

    private Trigger trigger;

    protected String parameter;

    protected String schedulerId;

    protected abstract void initialize();

    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
            scheduler.destroy();
            scheduler = null;
            isAlive = false;
        }

    }

    public void startScheduler() {
        if (!isAlive) {
            scheduler = new ThreadPoolTaskScheduler();
            scheduler.initialize();
            scheduler.schedule(() -> {
                if (isExecutable()) {
                    try {
                        SchedulerHistory schedulerHistory = new SchedulerHistory();
                        schedulerHistory.setSchedulerId(schedulerId);
                        schedulerHistory.setStartDt(new Date());
                        schedulerHistory.setIpAddress(InetAddress.getLocalHost().getHostAddress());
                        schedulerHistory.setCreateId("intadmin");
                        preExecute();
                        execute();
                        postExecute();
                        schedulerHistory.setEndDt(new Date());
                        schedulerHistoryRepository.save(schedulerHistory);

                        schedulerMapper.deleteHistory(schedulerId);
                        schedulerMapper.deleteAction(schedulerId);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, trigger);
            isAlive = true;
        }

    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void preExecute() {

    }

    private boolean isExecutable() {
        List<ESServer> allServer = esServerRepository.findAll();
        boolean isExecutable=true;

        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            Optional<ESServer> findThis= allServer.stream().filter((server) -> {
                return  ipAddress.equals(server.getIpAddress());
            }).findFirst();
    
            if(!findThis.isPresent()){
                return false;
            }
            
            ESServer thisServer = findThis.get();
            for (int i=(thisServer.getSeq()-1);i>-1;i--){
                int sequence = i;
                Optional<ESServer> matched = allServer.stream().filter((server) -> {
                    return  sequence == server.getSeq();
                }).findFirst();
                if(matched.isPresent()){
                    if(alive(matched.get())){
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExecutable;
    }

    private boolean alive(ESServer esServer) {
        Boolean result = false;
        HttpURLConnection con = null;
        try{
            String address = "http://"+esServer.getIpAddress()+":"+esServer.getPort()+"/api/system/common/alive";
            logger.debug(" check other esserver {}", address);
            URL url = new URL(address);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(500);
            con.setRequestMethod("GET");
            con.setDoOutput(false);
            con.connect();
            result = true;
        } catch (Exception e) {
        } finally{
            if(con!=null){
                con.disconnect();
            }
        }

        return result;
    }

    public abstract void execute();
    
    public void postExecute(){

    }

    public void  setTrigger(String expression){
        trigger = new CronTrigger(expression);
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

}
