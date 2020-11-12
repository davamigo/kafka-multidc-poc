package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vptech.kafkamulticlusterpoc.domain.entity.*;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerExtraDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service to read the brokers data from the the zookeeper server
 *
 * @author david.amigo
 */
@Service
public class ZookeeperBrokersReader implements ServerExtraDataReader {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperBrokersReader.class);

    /** Service to run a bash command */
    private final BashCommandRunner runner;

    /**
     * Autowired constructor
     *
     * @param runner the service to run bash commands
     */
    @Autowired
    public ZookeeperBrokersReader(BashCommandRunner runner) {
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

        ZookeeperData zookeeperData = ((ZookeeperData) server);

        LOGGER.debug("ZookeeperBrokersReader - reading broker ids from " + server.getName() + "...");
        String brokerIds = readBrokerIds(server);
        if (brokerIds.isEmpty()) {
            return;
        }

        LOGGER.debug("ZookeeperBrokersReader - The brokers ids are: " + brokerIds);
        zookeeperData.setBrokerIds(brokerIds);

        Integer[] brokerIdsArray = null;
        try {
            brokerIdsArray = new ObjectMapper().readValue(brokerIds, Integer[].class);
        } catch (JsonProcessingException exc) {
            LOGGER.error("ZookeeperBrokersReader - exception: " + exc.getMessage());
            exc.printStackTrace();
            return;
        }

        for (int brokerId : brokerIdsArray) {

            // Skip if broker data already exists
            if (!zookeeperData.getBrokerData(brokerId).equals("{}")) {
                break;
            }

            LOGGER.debug("ZookeeperBrokersReader - reading broker " + brokerId + " data from " + server.getName() + "...");
            String brokerData = readBrokerData(server, brokerId);
            if (brokerData.isEmpty()) {
                return;
            }

            LOGGER.debug("ZookeeperBrokersReader - The data read for broker " + brokerId + " is: " + brokerData);
            zookeeperData.setBrokerData(brokerId, brokerData);
        }
    }

    /**
     * Reads the broker ids from the Zookeeper server
     *
     * @param server the current data of the server
     */
    private String readBrokerIds(final ServerData server) {

        String command = String.format("docker exec -t %s zookeeper-shell.sh localhost:2181 ls /brokers/ids", server.getName());
        String output = runner.runCommand(command);
        String[] lines = output.split("\n");
        String brokerIds = lines[lines.length - 1];

        boolean matches = Pattern.matches("^\\[\\s*\\d+\\s*(,\\s*\\d+\\s*)*\\]$", brokerIds);
        if (!matches) {
            return "";
        }

        return brokerIds;
    }

    /**
     * Reads the broker extra data from the Zookeeper server
     *
     * @param server   the current data of the server
     * @param brokerId the broker id
     */
    private String readBrokerData(final ServerData server, int brokerId) {

        String command = String.format("docker exec -t %s zookeeper-shell.sh localhost:2181 get /brokers/ids/%d", server.getName(), brokerId);
        String output = runner.runCommand(command);
        String[] lines = output.split("\n");
        String brokerData = lines[lines.length - 1];

        boolean matches = Pattern.matches("^\\{.*\\}$", brokerData);
        if (!matches) {
            return "";
        }

        return brokerData;
    }
}
