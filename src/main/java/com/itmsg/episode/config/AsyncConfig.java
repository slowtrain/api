package com.itmsg.episode.config;
import org.springframework.core.task.TaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    public static final String SCHEDULER_TASK_EXECUTOR = "SchedulerExecutor";
    public static final String CONVERSION_EXECUTOR = "ConversionExecutor";
    
    @Bean(name=AsyncConfig.SCHEDULER_TASK_EXECUTOR)
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setQueueCapacity(2);
        taskExecutor.setThreadNamePrefix(AsyncConfig.SCHEDULER_TASK_EXECUTOR+"-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name=AsyncConfig.CONVERSION_EXECUTOR)
    public TaskExecutor conversionPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setQueueCapacity(2);
        taskExecutor.setThreadNamePrefix(AsyncConfig.CONVERSION_EXECUTOR+"-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}