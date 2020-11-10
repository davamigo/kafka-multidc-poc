package com.vptech.kafkamulticlusterpoc.configuration;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerType;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ServerConfiguration {

    /**
     * The environment object where to get the config options
     */
    private final Environment environment;

    /**
     * Autowired Constructor
     *
     * @param environment The environment object where to get the config options
     */
    @Autowired
    public ServerConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Creates a bean for the server data storage
     *
     * @return a new ServerDataStorage
     */
    @Bean
    public ServerDataStorage serverDataStorage() {

        String[] serverNames = environment.getProperty("app.config.server.names", String[].class, new String[0]);

        ServerDataStorage storage = new ServerDataStorage();
        for (String serverName : serverNames) {
            ServerData server = new ServerData();
            server.setName(serverName);

            String value = getConfigServerData(serverName, "location", "");
            if (!value.isEmpty()) {
                server.setLocation(ServerLocation.valueOf(value.toUpperCase()));
            }

            value = getConfigServerData(serverName, "type", "");
            if (!value.isEmpty()) {
                server.setType(ServerType.valueOf(value.toUpperCase()));
            }

            value = getConfigServerData(serverName, "address", "");
            if (!value.isEmpty()) {
                server.setAddress(value);
            }

            value = getConfigServerData(serverName, "port", "0");
            if (!value.isEmpty()) {
                server.setPort(Integer.parseInt(value));
            }

            storage.addServer(server);
        }
        return storage;
    }

    private String getConfigServerData(final String serverName, final String field, final String defaultValue) {
        final String key = "app.config.server.data." + serverName + "." + field;
        return environment.getProperty(key, defaultValue);
    }
}
