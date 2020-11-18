package com.vptech.kafkamulticlusterpoc.infrastructure.configuration;

import com.vptech.kafkamulticlusterpoc.domain.entity.KafkaBrokerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerType;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Apache Kafka
 *
 * Defines 3 beans for 3 different Kafka producer ack configurations: all, 1, 0
 *
 * @author david.amigo
 */
@EnableKafka
@Configuration
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
     * Kafka template bean for producing messages to Kafka with acks=all
     *
     * @return a new Kafka template for producing messages
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplateAcksAll() {
        return new KafkaTemplate<>(producerFactory("all"));
    }

    /**
     * Kafka template bean for producing messages to Kafka with acks=1
     *
     * @return a new Kafka template for producing messages
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplateAcks1() {
        return new KafkaTemplate<>(producerFactory("1"));
    }

    /**
     * Kafka template bean for producing messages to Kafka with acks=0
     *
     * @return a new Kafka template for producing messages
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplateAcks0() {
        return new KafkaTemplate<>(producerFactory("0"));
    }

    /**
     * Kafka producer factory for producing messages to Kafka
     *
     * @param acksConfig the configuration for acks: 0, 1 or all
     * @return the default kafka producer factory
     */
    private ProducerFactory<String, String> producerFactory(String acksConfig) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(acksConfig));
    }

    /**
     * Global configuration values for all Kafka producers
     *
     * @param acksConfig the configuration for acks: 0, 1 or all
     * @return the default configurations for all the Kafka producers
     */
    private Map<String, Object> producerConfigs(String acksConfig) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServersConfig());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put(ProducerConfig.ACKS_CONFIG, acksConfig);
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put(ProducerConfig.LINGER_MS_CONFIG, "2");

        return props;
    }

    /**
     * Builds the Kafka bootstrap servers connection string from the servers data
     *
     * @return the Kafka bootstrap servers connection string
     */
    private String getBootstrapServersConfig() {

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
