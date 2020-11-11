package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerStatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to check the status of a container running in docker
 *
 * @author david.amigo
 */
@Service
public class DockerContainerStatusChecker implements ServerStatusChecker {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DockerContainerStatusChecker.class);

    /** Service to run a bash command */
    private final BashCommandRunner runner;

    /**
     * Autowired constructor
     *
     * @param runner the service to run bash commands
     */
    @Autowired
    public DockerContainerStatusChecker(BashCommandRunner runner) {
        this.runner = runner;
    }

    /**
     * Checks and returns the status of a server
     *
     * @param serverName the name of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN in case of unable to check
     */
    @Override
    public ServerStatus check(final String serverName) {

        LOGGER.debug("DockerContainerStatusChecker - checking the status of " + serverName + "...");

        String command = String.format("docker ps --filter name=%s --format {{.Names}} ", serverName);
        String output = runner.runCommand(command);

        ServerStatus result = (output.isEmpty())
                ? ServerStatus.DOWN
                : (output.equals(serverName))
                        ? ServerStatus.UP
                        : ServerStatus.UNKNOWN;

        LOGGER.debug("DockerContainerStatusChecker - the status of " + serverName + " is " + result.toString());

        return result;
    }
}
