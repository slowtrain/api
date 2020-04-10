package com.itmsg.episode.scheduler;


import javax.annotation.PostConstruct;

import com.itmsg.episode.system.ESScheduler;

import org.springframework.stereotype.Component;

@Component(DisCIIntegrationScheduler.SCHEDULER_ID)
@ESScheduler(description = "Integration discovery ci")
public class DisCIIntegrationScheduler extends CommandScheduler {

    public static final String SCHEDULER_ID = "DisCIIntegrationScheduler";
    
    @PostConstruct
    @Override
    protected void initialize() {
        schedulerId = SCHEDULER_ID;
    }
     
}
