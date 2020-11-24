package com.vptech.kafkamulticlusterpoc.infrastructure.configuration;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerType;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;

/**
 * Base class for Apache Kafka configurations
 *
 * @author david.amigo
 */
public class KafkaConfiguration {

    /** The internal server data storage (Kafka Brokers) */
    private final ServerDataStorage storage;

    /**
     * Autowired constructor
     *
     * @param storage the internal server data storage
     */
    public KafkaConfiguration(ServerDataStorage storage) {
        this.storage = storage;
    }

    /**
     * Builds the Kafka bootstrap servers connection string from the servers data
     *
     * @return the Kafka bootstrap servers connection string
     */
    protected String getBootstrapServersConfig() {

        StringBuilder result = new StringBuilder();
        for (ServerData serverData : storage.getServers().values()) {
            if (serverData.getType() == ServerType.KAFKA_BROKER ) {
                String host = serverData.getHost();
                int port = serverData.getPort();
                if (!host.isEmpty() && port > 0) {
                    if (result.length() > 0) {
                        result.append(",");
                    }
                    result.append(host).append(":").append(port);
                }
            }
        }
        return result.toString();
    }
}
