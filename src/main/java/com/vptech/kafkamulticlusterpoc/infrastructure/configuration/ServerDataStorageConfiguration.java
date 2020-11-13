package com.vptech.kafkamulticlusterpoc.infrastructure.configuration;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataBuilder;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Configuration class to create the ServerDataStorage bean
 *
 * @author david.amigo
 */
@Configuration
public class ServerDataStorageConfiguration {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerDataStorageConfiguration.class);

    /** The environment object where to get the config options */
    private final Environment environment;

    /** The server data builder service */
    private final ServerDataBuilder builder;

    /**
     * Autowired Constructor
     *
     * @param environment the environment object where to get the config options
     * @param builder     the server data builder service
     */
    @Autowired
    public ServerDataStorageConfiguration(
            final Environment environment,
            final ServerDataBuilder builder
    ) {
        this.environment = environment;
        this.builder = builder;
    }

    /**
     * Creates a bean for the server data storage by reading the config from the environment
     *
     * @return a new ServerDataStorage
     */
    @Bean
    public ServerDataStorage serverDataStorage() {

        LOGGER.debug("ServerDataStorage - Creating the bean...");

        String[] serverNames = readServerNames();

        LOGGER.debug("ServerDataStorage - " + serverNames.length + " servers found.");

        ServerDataStorage storage = new ServerDataStorage();
        for (String serverName : serverNames) {

            ServerData server = createServer(serverName);
            if (server != null) {
                storage.addServer(server);
            }
        }
        return storage;
    }

    /**
     * Creates a server data object reading from the configuration
     *
     * @param serverName the name of the server
     * @return a new ServerData object or NULL
     */
    private ServerData createServer(final String serverName) {

        LOGGER.debug("ServerDataStorage - Creating server data for " + serverName + "...");

        String serverLocation = readServerData(serverName, "location", "");
        if (serverLocation.isEmpty()) {
            LOGGER.debug("ServerDataStorage - Server location not found in config for server " + serverName + "!");
            return null;
        }

        String serverType = readServerData(serverName, "type", "");
        if (serverType.isEmpty()) {
            LOGGER.debug("ServerDataStorage - Server type not found in config for server " + serverName + "!");
            return null;
        }

        ServerData server = builder.build(serverType.toUpperCase());
        if (server == null) {
            LOGGER.debug("ServerDataStorage - Invalid server type " + serverType + "!");
            return null;
        }

        String host = readServerData(serverName, "host", "");
        String port = readServerData(serverName, "port", "0");

        server.setName(serverName);
        server.setLocation(ServerLocation.valueOf(serverLocation.toUpperCase()));
        server.setHost(host);
        server.setPort(Integer.parseInt(port));

        LOGGER.debug("ServerDataStorage - Server data created for " + serverName + ": "
                + serverLocation + "/" + serverType + "/" + host + ":" + port);

        return server;
    }

    /**
     * Retrieves the server names from the environment
     *
     * @return a string array with the server names
     */
    private String[] readServerNames() {
        return environment.getProperty("app.config.server.names", String[].class, new String[0]);
    }

    /**
     * Retrieves the config of a server from the environment
     *
     * @param serverName   the name of the server
     * @param field        the name of the field
     * @param defaultValue the default value
     * @return the config value
     */
    private String readServerData(final String serverName, final String field, final String defaultValue) {
        final String key = "app.config.server.data." + serverName + "." + field;
        return environment.getProperty(key, defaultValue);
    }
}
