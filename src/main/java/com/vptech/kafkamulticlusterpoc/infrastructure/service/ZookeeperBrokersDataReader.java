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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Service to read the brokers data from the zookeeper server
 *
 * @author david.amigo
 */
@Service
public class ZookeeperBrokersDataReader implements ServerExtraDataReader {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperBrokersDataReader.class);

    /** Service to run a bash command */
    private final BashCommandRunner runner;

    /**
     * Autowired constructor
     *
     * @param runner the service to run bash commands
     */
    @Autowired
    public ZookeeperBrokersDataReader(BashCommandRunner runner) {
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

        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int brokerId : zookeeperData.getBrokerIdsList()) {

            // Skip if broker data already exists...
            if (zookeeperData.getBrokerData(brokerId).equals("{}")) {
                threadPool.submit(new RunnableBrokerDataReader(runner, zookeeperData, brokerId));
            }
        }

        try {
            threadPool.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    private static class RunnableBrokerDataReader implements Runnable {
        final BashCommandRunner runner;
        final ZookeeperData zookeeperData;
        final int brokerId;

        public RunnableBrokerDataReader(
                final BashCommandRunner runner,
                final ZookeeperData zookeeperData,
                final int brokerId
        ) {
            this.runner = runner;
            this.zookeeperData = zookeeperData;
            this.brokerId = brokerId;
        }

        @Override
        public void run() {

            LOGGER.debug("ZookeeperBrokersDataReader - reading broker data from " + zookeeperData.getName() + "/" + brokerId + "...");
            zookeeperData.setBrokerData(brokerId, "{ \"reading\": true }");

            String command = String.format("docker exec -t %s zookeeper-shell.sh localhost:2181 get /brokers/ids/%d", zookeeperData.getName(), brokerId);
            String output = runner.runCommand(command, 50, TimeUnit.SECONDS);
            String[] lines = output.split("\n");
            String brokerData = lines[lines.length - 1];

            boolean matches = Pattern.matches("^\\{.*}$", brokerData);
            if (!matches) {
                LOGGER.debug("ZookeeperBrokersDataReader - No broker data found from " + zookeeperData.getName() + "/" + brokerId);
                zookeeperData.setBrokerData(brokerId, "{}");
                return;
            }

            if (!brokerData.isEmpty()) {
                LOGGER.debug("ZookeeperBrokersDataReader - The read broker data from " + zookeeperData.getName() + "/" + brokerId + " is: " + brokerData);
                zookeeperData.setBrokerData(brokerId, brokerData);
            }
        }
    }
}
