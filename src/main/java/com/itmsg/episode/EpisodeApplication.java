package com.itmsg.episode;

import java.security.Security;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.itmsg.episode.app.system.scheduler.Scheduler;
import com.itmsg.episode.app.system.scheduler.SchedulerRepository;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;

import com.itmsg.episode.scheduler.TaskScheduler;
import com.itmsg.episode.system.ESScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EpisodeApplication extends SpringBootServletInitializer{
    
    @Resource
    private SchedulerRepository schedulerRepository;

    @Resource
    private ApplicationContext applicationContext;


    public static void main(String[] args) {
        SpringApplication.run(EpisodeApplication.class, args);
    }

    @PostConstruct
    public void initialize(){
        Security.setProperty("crypto.policy","unlimited");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        initializeScheduler();
    }

    
    private void initializeScheduler(){
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESScheduler.class);
        Iterator<String> keys = beans.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            TaskScheduler episodeScheduler = (TaskScheduler)beans.get(key);          
            List<Scheduler> schedulerList = schedulerRepository.findBySchedulerId(key);
            if(!schedulerList.isEmpty()){
                Scheduler scheduler = schedulerList.get(0);
                if("ACTIVE".equals(scheduler.getStatus())){
                    episodeScheduler.setParameter(scheduler.getParameter());
                    episodeScheduler.setTrigger(scheduler.getExpression());
                    episodeScheduler.startScheduler();
                }
            }
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EpisodeApplication.class);
    }

}
