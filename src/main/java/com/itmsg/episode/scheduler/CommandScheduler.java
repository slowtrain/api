package com.itmsg.episode.scheduler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandScheduler extends TaskScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initialize() {
    }

    @Override
    public void execute() {
        logger.debug("{} execute !!!!!! ", CommandScheduler.class.getName());

        String[] commands = parameter.split(",");
        ProcessBuilder processBuilder = new ProcessBuilder();
        Process process = null;
        for (String command : commands) {
            
            processBuilder.command(command);
            try {
                process = processBuilder.start();
                int exitval = process.waitFor();
                if(exitval == 0) {
                    logger.debug("{} success!!!!!",command);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }finally{
                if(process!=null){
                    process.destroy();
                }
            }
        }

        
    }
     
}
