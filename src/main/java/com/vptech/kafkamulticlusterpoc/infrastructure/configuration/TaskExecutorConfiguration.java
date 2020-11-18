package com.vptech.kafkamulticlusterpoc.infrastructure.configuration;

import com.vptech.kafkamulticlusterpoc.domain.service.MainProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * Configuration class to execute tasks on program startup
 *
 * Starts the Kafka producer (only when profile=docker)
 *
 * @author david.amigo
 */
@Configuration
public class TaskExecutorConfiguration {

    /** The main producer - sends messages to Kafka */
    private final MainProducer mainProducer;

    /**
     * Autowired constructor
     *
     * @param mainProducer the main producer
     */
    @Autowired
    public TaskExecutorConfiguration(MainProducer mainProducer) {
        this.mainProducer = mainProducer;
    }

    /**
     * Creates a new SimpleAsyncTaskExecutor that fires up a new Thread for each task, executing it asynchronously
     *
     * @return a new SimpleAsyncTaskExecutor
     */
    @Bean
    public TaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    @Profile("docker")
    public CommandLineRunner schedulingRunner(TaskExecutor asyncTaskExecutor) {
        return args -> asyncTaskExecutor.execute(() -> {
            while (true) {
                mainProducer.produce();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            }
        });
    }
}
