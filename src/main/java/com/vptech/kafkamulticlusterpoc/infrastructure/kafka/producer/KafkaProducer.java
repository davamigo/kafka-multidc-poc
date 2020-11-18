package com.vptech.kafkamulticlusterpoc.infrastructure.kafka.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Service to produce to Kafka
 *
 * Defines separate methods to produce a message to Kafka with different ack configurations: all, 1, 0
 *
 * @author david.amigo
 */
@Service
public class KafkaProducer {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    /** The Kafka templates for producing to Kafka */
    final private KafkaTemplate<String, String> kafkaTemplateAcksAll;
    final private KafkaTemplate<String, String> kafkaTemplateAcks1;
    final private KafkaTemplate<String, String> kafkaTemplateAcks0;

    /** Topic names */
    private final String topicAcksAll;
    private final String topicAcks1;
    private final String topicAcks0;

    /**
     * Autowired constructor
     *
     * @param kafkaTemplateAcksAll the Kafka template for producing messages to Kafka with acks=all
     * @param kafkaTemplateAcks1   the Kafka template for producing messages to Kafka with acks=1
     * @param kafkaTemplateAcks0   the Kafka template for producing messages to Kafka with acks=0
     */
    @Autowired
    public KafkaProducer(
            KafkaTemplate<String, String> kafkaTemplateAcksAll,
            KafkaTemplate<String, String> kafkaTemplateAcks1,
            KafkaTemplate<String, String> kafkaTemplateAcks0,
            @Value("${app.config.topic.acks_all}") String topicAcksAll,
            @Value("${app.config.topic.acks_1}") String topicAcks1,
            @Value("${app.config.topic.acks_0}") String topicAcks0
    ) {
        this.kafkaTemplateAcksAll = kafkaTemplateAcksAll;
        this.kafkaTemplateAcks1 = kafkaTemplateAcks1;
        this.kafkaTemplateAcks0 = kafkaTemplateAcks0;
        this.topicAcksAll = topicAcksAll;
        this.topicAcks1 = topicAcks1;
        this.topicAcks0 = topicAcks0;
    }

    /**
     * Sends a message to the provided topic with no key or partition.
     *
     * @param payload the data
     */
    public void produceToTopicAcksAll(String payload) {
        send(kafkaTemplateAcksAll, topicAcksAll, "all", payload);
    }

    /**
     * Sends a message to the provided topic with no key or partition.
     *
     * @param payload the data
     */
    public void produceToTopicAcks1(String payload) {
        send(kafkaTemplateAcks1, topicAcks1, "1", payload);
    }

    /**
     * Sends a message to the provided topic with no key or partition.
     *
     * @param payload the data
     */
    public void produceToTopicAcks0(String payload) {
        send(kafkaTemplateAcks0, topicAcks0, "0", payload);
    }

    /**
     * Send the data to the provided topic with no key or partition.
     *
     * @param template  the Kafka template object
     * @param topic     the name of the topic
     * @param acks      the acks configuration: 0, 1, all
     * @param payload   the payload
     */
    private void send(
            final KafkaTemplate<String, String> template,
            final String topic,
            final String acks,
            final String payload
    ) {
        log(topicAcks0, acks, payload);
        ListenableFuture<SendResult<String, String>> future = template.send(topic, payload);
        future.addCallback(new OnResultCallback(topic, acks, payload));
    }

    /**
     * Log the produced
     *
     * @param topic     the name of the topic
     * @param acks      the acks configuration: 0, 1, all
     * @param payload   the payload
     */
    private void log(final String topic, final String acks, final String payload) {
        LOGGER.info(
                "KafkaProducer - Topic: {} acks: {} payload: {} - Sending the message...",
                topic,
                acks,
                payload
        );
    }

    /**
     * Inner class to control de result of the template.send() operation
     */
    private static class OnResultCallback implements ListenableFutureCallback<SendResult<String, String>> {

        /** The name of the topic */
        String topic;

        /** the acks configuration: 0, 1, all */
        String acks;

        /** The payload sent */
        String payload;

        /**
         * Constructor
         *
         * @param topic     the name of the topic
         * @param acks      the acks config
         * @param payload   the payload
         */
        public OnResultCallback(String topic, String acks, String payload) {
            this.topic = topic;
            this.acks = acks;
            this.payload = payload;
        }

        /**
         * Error occurred on kafkaTemplate.send()
         *
         * @param exc the failure
         */
        @Override
        public void onFailure(Throwable exc) {
            LOGGER.error(
                    "KafkaProducer - Error: {} Topic: {} acks: {} payload: {}",
                    exc.getMessage(),
                    topic,
                    acks,
                    payload
            );
            exc.printStackTrace();
        }

        /**
         * Success kafkaTemplate.send()
         *
         * @param result the result
         */
        @Override
        public void onSuccess(SendResult<String, String> result) {
            String topic = this.topic;
            int partition = -1;
            long offset = -1;
            if (result != null) {
                RecordMetadata metadata = result.getRecordMetadata();
                if (metadata != null) {
                    topic = metadata.topic();
                    partition = metadata.partition();
                    offset = metadata.offset();
                }
            }
            LOGGER.info(
                    "KafkaProducer - Topic: {} partition: {} offset: {} acks: {} payload: {} - Message sent!",
                    topic,
                    partition,
                    offset,
                    this.acks,
                    this.payload
            );
        }
    }
}
