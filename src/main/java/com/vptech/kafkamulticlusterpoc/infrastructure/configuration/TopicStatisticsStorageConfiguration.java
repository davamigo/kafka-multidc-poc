package com.vptech.kafkamulticlusterpoc.infrastructure.configuration;

import com.vptech.kafkamulticlusterpoc.domain.service.TopicStatisticsStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class to create the ServerDataStorage bean
 *
 * @author david.amigo
 */
@Configuration
public class TopicStatisticsStorageConfiguration {

    /** The environment object where to get the config options */
    private final Environment environment;

    /**
     * Autowired Constructor
     *
     * @param environment the environment object where to get the config options
     */
    public TopicStatisticsStorageConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Creates a bean for the topic statistics storage by reading the topics config from the environment
     *
     * @return a new ServerDataStorage
     */
    @Bean
    TopicStatisticsStorage topicStatisticsStorage() {
        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        for (String topic : readTopicNames()) {
            storage.addTopic(topic);
        }
        return storage;
    }

    /**
     * Retrieves the real names of the topics from the environment
     *
     * @return a string array with the real names of the topics
     */
    private List<String> readTopicNames() {

        List<String> topics = new ArrayList<>();
        String[] topicLogicalNames = environment.getProperty("app.config.kafka.topics", String[].class, new String[0]);
        for (String topicLogicalName : topicLogicalNames) {
            topics.add(readRealTopicName(topicLogicalName));
        }
        return topics;
    }

    /**
     * Return the real name of the topic reading from the environment
     *
     * @param topicLogicalName the logical name of the topic
     * @return the real name of the topic
     */
    private String readRealTopicName(String topicLogicalName) {
        return environment.getProperty("app.config.kafka.topic." + topicLogicalName);
    }
}
