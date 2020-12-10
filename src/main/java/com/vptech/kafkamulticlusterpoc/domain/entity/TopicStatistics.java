package com.vptech.kafkamulticlusterpoc.domain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for handling topic statistics:
 *
 * - Count of produced and consumed messages.
 * - List of messages sent to the producer.
 * - List of messages produced but not consumed.
 * - List of messages consumed but not produced.
 * - List of messages not produced because an error.
 *
 * @author david.amigo
 */
public class TopicStatistics {

    /** The name of the topic */
    final String name;

    /** Count of produced & consumed successfully messages */
    long producedCount;
    long consumedCount;

    /** Lists of messages */
    final List<String> messagesSentToProducer;
    final List<String> messagesProducedNotConsumed;
    final List<String> messagesConsumedNotProduced;
    final List<String> messagesNotProducedBecauseAnError;

    /** Allowed actions over the messages */
    enum Action {
        SENT_TO_BE_PRODUCED,
        PRODUCED_SUCCESSFULLY,
        CONSUMED_SUCCESSFULLY,
        NOT_PRODUCED
    }

    /**
     * Constructor
     *
     * @param name the name of the topic
     */
    public TopicStatistics(String name) {
        this.name = name;
        this.producedCount = 0;
        this.consumedCount = 0;
        this.messagesSentToProducer = new ArrayList<>();
        this.messagesProducedNotConsumed = new ArrayList<>();
        this.messagesConsumedNotProduced = new ArrayList<>();
        this.messagesNotProducedBecauseAnError = new ArrayList<>();
    }

    /**
     * A message has been sent to be produced
     *
     * @param payload the payload of the message
     */
    public void addMessageSentToBeProduced(String payload) {
        store(Action.SENT_TO_BE_PRODUCED, payload);
    }

    /**
     * A message has been produced successfully
     *
     * @param payload the payload of the message
     */
    public void addMessageProducedSuccessfully(String payload) {
        store(Action.PRODUCED_SUCCESSFULLY, payload);
    }

    /**
     * A message has been consumed successfully
     *
     * @param payload the payload of the message
     */
    public void addConsumedMessageSuccessfully(String payload) {
        store(Action.CONSUMED_SUCCESSFULLY, payload);
    }

    /**
     * An error occurred producing a message
     *
     * @param payload the payload of the message
     */
    public void addMessageNotProduced(String payload) {
        store(Action.NOT_PRODUCED, payload);
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
     * @return the total number of messages sent to the producer
     */
    public long getMessagesSentToProducerCount() {
        return messagesSentToProducer.size();
    }

    /**
     * @return a copy of the messages sent to the producer list
     */
    public List<String> getMessagesSentToProducer() {
        return new ArrayList<>(messagesSentToProducer);
    }

    /**
     * @return the total number of messages produced but not consumed
     */
    public long getMessagesProducedNotConsumedCount() {
        return messagesProducedNotConsumed.size();
    }

    /**
     * @return a copy of the messages produced but not consumed list
     */
    public List<String> getMessagesProducedNotConsumed() {
        return new ArrayList<>(messagesProducedNotConsumed);
    }

    /**
     * @return the total number of messages consumed but not produced
     */
    public long getMessagesConsumedNotProducedCount() {
        return messagesConsumedNotProduced.size();
    }

    /**
     * @return a copy of the messages consumed but not produced list
     */
    public List<String> getMessagesConsumedNotProduced() {
        return new ArrayList<>(messagesConsumedNotProduced);
    }

    /**
     * @return the total number of messages unable to produce
     */
    public long getMessagesNotProducedBecauseAnErrorCount() {
        return messagesNotProducedBecauseAnError.size();
    }

    /**
     * @return a copy of the unable to produce messages list
     */
    public List<String> getMessagesNotProducedBecauseAnError() {
        return new ArrayList<>(messagesNotProducedBecauseAnError);
    }

    /**
     * Synchronized function to store the data in the object
     *
     * @param action  SENT_TO_PRODUCER, PRODUCED_NOT_CONSUMED, CONSUMED_NOT_PRODUCED, NOT_PRODUCED_BECAUSE_AN_ERROR
     * @param payload the payload of the message
     */
    synchronized private void store(final Action action, final String payload) {
        switch (action) {

            case SENT_TO_BE_PRODUCED:
                if (!messagesSentToProducer.contains(payload)) {
                    messagesSentToProducer.add(payload);
                }
                break;

            case PRODUCED_SUCCESSFULLY:
                producedCount++;
                messagesSentToProducer.remove(payload);
                if (messagesConsumedNotProduced.contains(payload)) {
                    messagesConsumedNotProduced.remove(payload);
                } else if (!messagesProducedNotConsumed.contains(payload)){
                    messagesProducedNotConsumed.add(payload);
                }
                break;

            case CONSUMED_SUCCESSFULLY:
                consumedCount++;
                messagesSentToProducer.remove(payload);
                if (messagesProducedNotConsumed.contains(payload)) {
                    messagesProducedNotConsumed.remove(payload);
                } else if (!messagesConsumedNotProduced.contains(payload)){
                    messagesConsumedNotProduced.add(payload);
                }
                break;

            case NOT_PRODUCED:
                messagesSentToProducer.remove(payload);
                messagesNotProducedBecauseAnError.add(payload);
                break;
        }
    }
}
