package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.TopicStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service to store the live statistics of the topics
 *
 * Note: No @Service annotation because the bean is created in a @Configuration class called
 * TopicStatisticsStorageConfiguration - to load the initial data
 *
 * @author david.amigo
 */
public class TopicStatisticsStorage {

    /** The map of the current statistics of the topics */
    private final Map<String, TopicStatistics> statistics  = new HashMap<>();

    /**
     * Get a list of topic names
     *
     * @return a list of topic names
     */
    public List<String> getAllTopics() {
        return new ArrayList<>(statistics.keySet());
    }

    /**
     * Get the current statistics of all the topics
     *
     * @return a list of topic statistics
     */
    public List<TopicStatistics> getAllTopicStatistics() {
        return new ArrayList<>(statistics.values());
    }

    /**
     * Get the current statistics of a topic
     *
     * @param topicName the name of the topic
     * @return the statistics of the topic or null
     */
    public TopicStatistics getTopicStatistics(final String topicName) {
        return statistics.get(topicName);
    }

    /**
     * Adds a new topic to the storage if it doesn't exist
     *
     * @param topicName the name of the topic
     */
    public void addTopic(final String topicName) {
        statistics.putIfAbsent(topicName, new TopicStatistics(topicName));
    }

    /**
     * A message has been produced successfully
     *
     * @param topicName the name of the topic
     * @param payload   the payload of the message
     * @throws IllegalArgumentException when the topic doesn't exist in the storage
     */
    public void addProducedMessage(final String topicName, final String payload) {
        checkTopicExist(topicName);
        statistics.get(topicName).addProducedMessage(payload);
    }

    /**
     * A message has been consumed successfully
     *
     * @param topicName the name of the topic
     * @param payload the payload of the message
     * @throws IllegalArgumentException when the topic doesn't exist in the storage
     */
    public void addConsumedMessage(final String topicName, final String payload) {
        checkTopicExist(topicName);
        statistics.get(topicName).addConsumedMessage(payload);
    }

    /**
     * An error occurred producing a message
     *
     * @param topicName the name of the topic
     * @param payload the payload of the message
     * @throws IllegalArgumentException when the topic doesn't exist in the storage
     */
    public void addErrorProducingMessage(final String topicName, final String payload) {
        checkTopicExist(topicName);
        statistics.get(topicName).addErrorProducingMessage(payload);
    }

    /**
     * Checks if the topic exist in the storage
     *
     * @param topicName the name of the topic
     * @throws IllegalArgumentException when the topic doesn't exist in the storage
     */
    private void checkTopicExist(final String topicName) {
        if (!statistics.containsKey(topicName)) {
            throw new IllegalArgumentException("Topic " + topicName + " not found in the storage!");
        }
    }
}
