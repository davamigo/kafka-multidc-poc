package com.vptech.kafkamulticlusterpoc.domain.service;

/**
 * Interface to a service to produce messages to Kafka
 *
 * @author david.amigo
 */
public interface MainProducer {

    /**
     * Produces predetermined messages to Kafka topics
     */
    void produce();
}
