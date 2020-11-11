package com.vptech.kafkamulticlusterpoc.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;

/**
 * Configuration class to enable scheduling and define the number of threads
 *
 * @author david.amigo
 */
@Configuration
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {

    /** The number of threads for the task scheduler */
    private String threads;

    /**
     * Autowired constructor
     *
     * @param threads the number of threads of the task scheduler
     */
    public SchedulerConfiguration(
            @Value("${app.config.scheduler.threads:1}") String threads
    ) {
        this.threads = threads;
    }

    /**
     * Callback allowing a {@link TaskScheduler
     * TaskScheduler} and specific {@link Task Task}
     * instances to be registered against the given the {@link ScheduledTaskRegistrar}.
     *
     * @param taskRegistrar the registrar to be configured.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        int poolSize = Integer.parseInt(threads);
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(poolSize);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
