package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Primary service to check the status of a server. It redirects the call to a specific service.
 *
 * @author david.amigo
 */
@Service
@Primary
public class PrimaryServerStatusChecker implements ServerStatusChecker {

    /** List of available services to check the status of a server */
    final List<ServerStatusChecker> services;

    /**
     * Autowired constructor
     *
     * @param services the list of available services to check the status of a server
     */
    @Autowired
    public PrimaryServerStatusChecker(List<ServerStatusChecker> services) {
        this.services = new ArrayList<>(services);
        this.services.remove(this);
    }

    /**
     * Returns the list of valid locations tested by the service: docker, network, ...
     *
     * @return the list of valid locations
     */
    @Override
    public List<ServerLocation> validLocations() {
        return Collections.emptyList();
    }

    /**
     * Checks and returns the status of a server
     *
     * @param server the data of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN in case of unable to check
     */
    @Override
    public ServerStatus check(ServerData server) {
        for (ServerStatusChecker checker : this.services) {
            ServerLocation location = server.getLocation();
            if (checker.validLocations().contains(location)) {
                return checker.check(server);
            }
        }
        return ServerStatus.UNKNOWN;
    }
}
