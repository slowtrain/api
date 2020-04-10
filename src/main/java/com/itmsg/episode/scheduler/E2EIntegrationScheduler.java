package com.itmsg.episode.scheduler;

import javax.annotation.PostConstruct;

import com.itmsg.episode.system.ESScheduler;

import org.springframework.stereotype.Component;

@Component(E2EIntegrationScheduler.SCHEDULER_ID)
@ESScheduler(description = "Integration E2E")
public class E2EIntegrationScheduler extends CommandScheduler {

    public static final String SCHEDULER_ID = "E2EIntegrationScheduler";
    
    @PostConstruct
    @Override
    protected void initialize() {
        schedulerId = SCHEDULER_ID;
    }
     
}
