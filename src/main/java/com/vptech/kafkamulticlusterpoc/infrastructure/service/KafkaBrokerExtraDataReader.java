package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vptech.kafkamulticlusterpoc.domain.entity.*;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerExtraDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to read the extra data of a kafka broker server
 *
 * @author david.amigo
 */
@Service
public class KafkaBrokerExtraDataReader implements ServerExtraDataReader {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaBrokerExtraDataReader.class);

    /** Storage for the server data */
    private final ServerDataStorage storage;

    /**
     * Autowired constructor
     *
     * @param storage the storage for the server data
     */
    @Autowired
    public KafkaBrokerExtraDataReader(ServerDataStorage storage) {
        this.storage = storage;
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
                && server.getType() == ServerType.KAFKA_BROKER
                && server.getStatus() == ServerStatus.UP
                && server instanceof KafkaBrokerData);
    }

    /**
     * Reads extra data for the Kafka broker.
     *
     * Extracts the `broker.id` and the `broker.rack` from the data already read from Zookeeper
     * and stored in the `ServerDataStorage` service.
     *
     * @param server the current data of the server
     */
    @Override
    public void readExtraData(final ServerData server) {

        if (!isEligible(server)) {
            return;
        }

        // Skip if Kafka broker data has already been read...
        KafkaBrokerData kafkaBrokerData = ((KafkaBrokerData) server);
        if (kafkaBrokerData.getBrokerId() < 1) {

            LOGGER.debug("KafkaBrokerExtraDataReader - reading extra data for Kafka broker " + server.getName() + "...");

            for (ServerData sd : storage.getServers().values()) {
                if (sd.getType() == ServerType.ZOOKEEPER
                        && sd instanceof ZookeeperData) {
                    if (extractServerData(kafkaBrokerData, ((ZookeeperData) sd))) {
                        break;
                    }
                }
            }

            LOGGER.debug("KafkaBrokerExtraDataReader - finished reading extra data for Kafka broker " + server.getName());
        }
    }

    /**
     * Extracts extra data from a zookeeper data object to fill a kafka broker extra data
     *
     * @param kafkaBrokerData the Kafka broker server data object
     * @param zookeeperData   the Zookeeper server data object
     * @return true if successful data extraction
     */
    private boolean extractServerData(
            final KafkaBrokerData kafkaBrokerData,
            final ZookeeperData zookeeperData
    ) {
        LOGGER.debug("KafkaBrokerExtraDataReader - checking Zookeeper server " + zookeeperData.getName() + " for extra data...");

        for (int brokerId : zookeeperData.getBrokerIdsList()) {
            String brokerJsonData = zookeeperData.getBrokerData(brokerId);

            JsonNode jsonNode;
            try {
                jsonNode = (new ObjectMapper()).readTree(brokerJsonData);
            } catch (JsonProcessingException exc) {
                // Ignore exception
                return false;
            }

            if (jsonNode.has("host")
                    && jsonNode.has("rack")) {
                String host = jsonNode.get("host").asText("");
                if (host.equals(kafkaBrokerData.getName())) {
                    LOGGER.debug("KafkaBrokerExtraDataReader - extra data found for Kafka broker " + kafkaBrokerData.getName() + " in Zookeeper " + zookeeperData.getName());
                    String brokerRack = jsonNode.get("rack").asText("");
                    kafkaBrokerData.setBrokerRack(brokerRack);
                    kafkaBrokerData.setBrokerId(brokerId);
                    return true;
                }
            }
        }

        return false;
    }
}
