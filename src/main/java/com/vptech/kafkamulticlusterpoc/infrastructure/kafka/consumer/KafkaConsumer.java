package com.vptech.kafkamulticlusterpoc.infrastructure.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * Service to consume from Kafka
 *
 * @author david.amigo
 */
@Component
public class KafkaConsumer {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    /**
     * Kafka listener for topic "topic.acks.all"
     *
     * @param value     the value of the message in the topic
     * @param ack       the acknowledgment object
     * @param topic     the name of the topic
     * @param partition the ID of the partition
     */
    @KafkaListener(
            topics = "${app.config.kafka.topic.acks_all}",
            groupId = "${app.name}",
            containerFactory = "containerFactory",
            autoStartup = "${app.config.kafka.consumer.enabled:false}"
    )
    public void listenAcksAll(
            String value,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ) {
        processMessage(value, topic, partition);
        ack.acknowledge();
    }

    /**
     * Kafka listener for topic "topic.acks.1"
     *
     * @param value     the value of the message in the topic
     * @param ack       the acknowledgment object
     * @param topic     the name of the topic
     * @param partition the ID of the partition
     */
    @KafkaListener(
            topics = "${app.config.kafka.topic.acks_1}",
            groupId = "${app.name}",
            containerFactory = "containerFactory",
            autoStartup = "${app.config.kafka.consumer.enabled:false}"
    )
    public void listenAcks1(
            String value,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ) {
        processMessage(value, topic, partition);
        ack.acknowledge();
    }

    /**
     * Kafka listener for topic "topic.acks.0"
     *
     * @param value     the value of the message in the topic
     * @param ack       the acknowledgment object
     * @param topic     the name of the topic
     * @param partition the ID of the partition
     */
    @KafkaListener(
            topics = "${app.config.kafka.topic.acks_0}",
            groupId = "${app.name}",
            containerFactory = "containerFactory",
            autoStartup = "${app.config.kafka.consumer.enabled:false}"
    )
    public void listenAcks0(
            String value,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ) {
        processMessage(value, topic, partition);
        ack.acknowledge();
    }

    /**
     * Processes a message read from a Kafka topic
     *
     * @param value     the value of the message in the topic
     * @param topic     the name of the topic
     * @param partition the ID of the partition
     */
    private void processMessage(
            String value,
            String topic,
            int partition
    ) {
        LOGGER.info("KafkaConsumer - Message read from Kafka - topic {} - partition {} - value {}",
                topic,
                partition,
                value
        );

        // TODO process the consumed message
    }
}
