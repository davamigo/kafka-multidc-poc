package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.service.MainProducer;
import com.vptech.kafkamulticlusterpoc.infrastructure.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to produce messages to Kafka
 *
 * Sends the seconds since 2020-01-01T00:00:00 to 3 different topics with different ack configuration: all, 1, 0
 *
 * @author david.amigo
 */
@Service
public class KafkaMainProducer implements MainProducer {

    /** The Kafka producer */
    private final KafkaProducer kafkaProducer;

    /**
     * Autowired constructor
     *
     * @param kafkaProducer the Kafka producer
     */
    @Autowired
    public KafkaMainProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Produces predetermined messages to Kafka topics.
     *
     * Generates a natural number based in the current date and produces it to a Kafka topic with
     * 3 different configurations of acks=all, 1, 0
     */
    @Override
    public void produce() {

        long currentDate = System.currentTimeMillis() / 1000;
        long sourceDate = 1577836800; // 2020-01-01
        long seconds = currentDate - sourceDate;
        String payload = String.valueOf(seconds);

        kafkaProducer.produceToTopicAcksAll(payload);
        kafkaProducer.produceToTopicAcks1(payload);
        kafkaProducer.produceToTopicAcks0(payload);
    }
}
