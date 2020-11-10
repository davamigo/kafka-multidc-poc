package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.KafkaBrokerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerType;
import com.vptech.kafkamulticlusterpoc.domain.entity.ZookeeperData;
import org.springframework.stereotype.Service;

/**
 * Builder service for ServerData entities
 *
 * @author david.amigo
 */
@Service
public class ServerDataBuilder {

    /**
     * Builds a ServerData object depending on the type
     *
     * @param typeText the server type: ZOOKEEPER or KAFKA_BROKER
     * @return a new ServerData object or null
     */
    public ServerData build(final String typeText) {

        ServerType type = ServerType.NULL;
        try {
            type = ServerType.valueOf(typeText);
        } catch (IllegalArgumentException | NullPointerException exc) {
            exc.printStackTrace();
        }

        switch (type) {
            case ZOOKEEPER:
                return new ZookeeperData();
            case KAFKA_BROKER:
                return new KafkaBrokerData();
            default:
                return null;
        }
    }
}
