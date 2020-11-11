package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.*;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerExtraDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to read the ID in the quorum  of the zookeeper server
 *
 * @author david.amigo
 */
@Service
public class ZookeeperIdReader implements ServerExtraDataReader {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperIdReader.class);

    /** Service to run a bash command */
    private final BashCommandRunner runner;

    /**
     * Autowired constructor
     *
     * @param runner the service to run bash commands
     */
    @Autowired
    public ZookeeperIdReader(BashCommandRunner runner) {
        this.runner = runner;
    }

    /**
     * Checks if the service is eligible for a given server
     *
     * @param server the current data of the server
     * @return true if
     */
    @Override
    public boolean isEligible(final ServerData server) {
        return (server.getLocation() == ServerLocation.DOCKER
                && server.getType() == ServerType.ZOOKEEPER
                && server.getStatus() == ServerStatus.UP
                && server instanceof ZookeeperData);
    }

    /**
     * Reads extra data for the server
     *
     * @param server the current data of the server
     */
    @Override
    public void readExtraData(final ServerData server) {

        if(!isEligible(server)) {
            return;
        }

        LOGGER.debug("ZookeeperIdReader - reading ID from " + server.getName() + "...");

        String command = String.format("docker exec -t %s cat /tmp/zookeeper/myid ", server.getName());
        String output = runner.runCommand(command);

        try {
            int zookeeperId = Integer.parseInt(output);
            if (zookeeperId > 0) {
                LOGGER.debug("ZookeeperIdReader - The id of the server " + server.getName() + " is " + zookeeperId);
                ZookeeperData zookeeperData = ((ZookeeperData) server);
                zookeeperData.setZookeeperId(zookeeperId);
            }
        } catch (NumberFormatException exc) {
            LOGGER.error("ZookeeperIdReader - exception: " + exc.getMessage());
            exc.printStackTrace();
        }
    }
}
