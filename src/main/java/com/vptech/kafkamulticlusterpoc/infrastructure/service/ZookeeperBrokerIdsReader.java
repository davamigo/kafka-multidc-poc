package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerType;
import com.vptech.kafkamulticlusterpoc.domain.entity.ZookeeperData;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerExtraDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Service to read the broker ids from the zookeeper server
 *
 * @author david.amigo
 */
@Service
public class ZookeeperBrokerIdsReader implements ServerExtraDataReader {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperBrokerIdsReader.class);

    /** Service to run a bash command */
    private final BashCommandRunner runner;

    /**
     * Autowired constructor
     *
     * @param runner the service to run bash commands
     */
    @Autowired
    public ZookeeperBrokerIdsReader(BashCommandRunner runner) {
        this.runner = runner;
    }

    /**
     * Checks if the service is eligible for a given server
     *
     * @param server the current data of the server
     * @return true if
     */
    @Override
    public boolean isEligible(ServerData server) {
        return (server.getLocation() == ServerLocation.DOCKER
                && server.getType() == ServerType.ZOOKEEPER
                && server.getStatus() == ServerStatus.UP
                && server instanceof ZookeeperData);
    }

    /**
     * Reads extra data for the Zookeeper server
     *
     * @param server the current data of the server
     */
    @Override
    public void readExtraData(final ServerData server) {

        if (!isEligible(server)) {
            return;
        }

        // Skip if broker ids have already been read...
        ZookeeperData zookeeperData = ((ZookeeperData) server);
        if (zookeeperData.getBrokerIds().equals("[]")) {

            LOGGER.debug("ZookeeperBrokerIdsReader - reading broker ids from " + server.getName() + "...");

            String command = String.format("docker exec -t %s zookeeper-shell.sh localhost:2181 ls /brokers/ids", server.getName());
            String output = runner.runCommand(command);
            String[] lines = output.split("\n");
            String brokerIds = lines[lines.length - 1];

            boolean matches = Pattern.matches("^\\[\\s*\\d+\\s*(,\\s*\\d+\\s*)*]$", brokerIds);
            if (!matches) {
                LOGGER.debug("ZookeeperBrokerIdsReader - invalid broker ids data received from " + server.getName());
                return;
            }

            if (brokerIds.isEmpty()) {
                return;
            }

            LOGGER.debug("ZookeeperBrokerIdsReader - The brokers ids for " + server.getName() + " are: " + brokerIds);
            zookeeperData.setBrokerIds(brokerIds);
        }
    }
}
