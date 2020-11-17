package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerStatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

/**
 * Service to check the status of a server + port in the network by opening a socket
 *
 * @author david.amigo
 */
@Service
public class NetworkSocketStatusChecker implements ServerStatusChecker {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSocketStatusChecker.class);

    /**
     * Returns the list of valid locations tested by the service: docker, network, ...
     *
     * @return the list of valid locations
     */
    @Override
    public List<ServerLocation> validLocations() {
        return Collections.singletonList(ServerLocation.NETWORK);
    }

    /**
     * Checks and returns the status of a server
     *
     * @param server the data of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN in case of unable to check
     */
    @Override
    public ServerStatus check(final ServerData server) {

        LOGGER.debug("BasicStatusChecker - checking the status of " + server.getName() + "...");

        ServerStatus result;
        try (Socket socket = new Socket(server.getHost(), server.getPort())) {
            result = ServerStatus.UP;
        } catch (IOException exc) {
            /* ignore error */
            result = ServerStatus.DOWN;
        }

        LOGGER.debug("BasicStatusChecker - the status of " + server.getName() + " is " + result.toString());

        return result;
    }
}
