package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;

/**
 * Interface to a service to check the status of a server
 *
 * @author david.amigo
 */
public interface ServerStatusChecker {

    /**
     * Checks and returns the status of a server
     *
     * @param server the name of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN in case of unable to check
     */
    ServerStatus check(final String server);
}
