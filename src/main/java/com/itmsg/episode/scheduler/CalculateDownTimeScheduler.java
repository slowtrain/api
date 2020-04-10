package com.itmsg.episode.scheduler;

import javax.annotation.PostConstruct;

import com.itmsg.episode.system.ESScheduler;

import org.springframework.stereotype.Component;

@Component(CalculateDownTimeScheduler.SCHEDULER_ID)
@ESScheduler(description = "Calcualte downtime of CI")
public class CalculateDownTimeScheduler extends CommandScheduler {
    
    public static final String SCHEDULER_ID = "CalculateDownTimeScheduler";
    
    @PostConstruct
    @Override
    protected void initialize() {
        schedulerId = SCHEDULER_ID;
    }
     
}
