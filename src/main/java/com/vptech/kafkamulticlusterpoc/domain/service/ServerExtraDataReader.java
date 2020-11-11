package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;

/**
 * Interface to a service to fulfill the server extra data (for zookeeper or kafka broker)
 *
 * @author david.amigo
 */
public interface ServerExtraDataReader {

    /**
     * Checks if the service is eligible for a given server
     *
     * @param server the current data of the server
     * @return true if
     */
    boolean isEligible(final ServerData server);

    /**
     * Reads extra data for the server
     *
     * @param server the current data of the server
     */
    void readExtraData(final ServerData server);
}
