package com.itmsg.episode.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.disci.DisCIService;
import com.itmsg.episode.system.ESScheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(CIPromoteScheduler.SCHEDULER_ID)
@ESScheduler(description="promote discovery ci to authorized ci")
public class CIPromoteScheduler extends TaskScheduler {

    public static final String SCHEDULER_ID = "CIPromoteScheduler";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @PostConstruct
    @Override
    protected void initialize() {
        schedulerId = SCHEDULER_ID;
    }

    @Resource
    DisCIService disCIService;

    @Override
    public void execute() {
        logger.debug("{} execute !!!!!! ",DisCIIntegrationScheduler.class.getName());

        String[] dataSources = parameter.split(",");
        
        for(String dataSource:dataSources){
            disCIService.promote(dataSource);
        }

        disCIService.insertRPASystem();
        disCIService.insertDisCIMapToCI();
        disCIService.promoteRelations();     
    }
    
}
