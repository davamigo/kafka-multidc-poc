package com.vptech.kafkamulticlusterpoc.domain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for handling topic data
 *
 * @author david.amigo
 */
public class TopicData {

    /** The name of the topic */
    final String name;

    /** Count of produced & consumed successfully messages */
    long producedCount;
    long consumedCount;

    /** List of messages produced but not consumed or vice versa */
    final List<String> pendingMessages;
    final List<String> outOfSeqMessages;

    /** List of messages unable to produce */
    final List<String> unableToProduceMessages;

    /**
     * Constructor
     *
     * @param name the name of the topic
     */
    public TopicData(String name) {
        this.name = name;
        this.producedCount = 0;
        this.consumedCount = 0;
        this.pendingMessages = new ArrayList<>();
        this.outOfSeqMessages = new ArrayList<>();
        this.unableToProduceMessages = new ArrayList<>();
    }

    /**
     * A message has been produced successfully
     *
     * @param payload the payload of the message
     */
    public void messageProduced(String payload) {
        producedCount++;
        if (outOfSeqMessages.contains(payload)) {
            outOfSeqMessages.remove(payload);
        } else {
            pendingMessages.add(payload);
        }
    }

    /**
     * A message has been consumed successfully
     *
     * @param payload the payload of the message
     */
    public void messageConsumed(String payload) {
        consumedCount++;
        if (pendingMessages.contains(payload)) {
            pendingMessages.remove(payload);
        } else {
            outOfSeqMessages.add(payload);
        }
    }

    /**
     * An error occurred producing a message
     *
     * @param payload the payload of the message
     */
    public void errorProducingMessage(String payload) {
        unableToProduceMessages.add(payload);
    }

    /**
     * @return the name of the topic
     */
    public String getName() {
        return name;
    }

    /**
     * @return the total number of messages produced successfully
     */
    public long getProducedCount() {
        return producedCount;
    }

    /**
     * @return the total number of messages consumed successfully
     */
    public long getConsumedCount() {
        return consumedCount;
    }

    /**
     * @return the total number of messages unable to produce
     */
    public long getUnableToProduceCount() {
        return unableToProduceMessages.size();
    }

    /**
     * @return the total number of messages produced but not consumed
     */
    public long getPendingMessagesCount() {
        return pendingMessages.size();
    }

    /**
     * @return the total number of messages consumed but not produced
     */
    public long getOutOfSeqMessagesCount() {
        return outOfSeqMessages.size();
    }
}
