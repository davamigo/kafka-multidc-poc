package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;

import java.util.List;

/**
 * Interface to a service to check the status of a server
 *
 * @author david.amigo
 */
public interface ServerStatusChecker {

    /**
     * Returns the list of valid locations tested by the service: docker, network, ...
     *
     * @return the list of valid locations
     */
    List<ServerLocation> validLocations();

    /**
     * Checks and returns the status of a server
     *
     * @param server the data of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN in case of unable to check
     */
    ServerStatus check(final ServerData server);
}
