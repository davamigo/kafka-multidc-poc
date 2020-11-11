package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Service to store the data and the status of the servers
 *
 * @author david.amigo
 */
public class ServerDataStorage {

    /** Server data storage - static map */
    private final Map<String, ServerData> servers = new HashMap<>();

    /**
     * Adds a new server with empty data
     *
     * @param serverName the name of the server
     */
    public void addServer(final String serverName) {
        if (!servers.containsKey(serverName)) {
            servers.put(serverName, (new ServerData()).setName(serverName));
        }
    }

    /**
     * Adds a new server
     *
     * @param server the name of the server
     */
    public void addServer(final ServerData server) {
        servers.put(server.getName(), server);
    }

    /**
     * Stores the server status
     *
     * @param serverName the name of the server
     * @param status the status of the server
     * @return the previous status of the server
     */
    public ServerStatus setStatus(
            final String serverName,
            final ServerStatus status
    ) {
        ServerData server = servers.get(serverName);
        ServerStatus result = null;
        if (server != null) {
            result = server.getStatus();
            server.setStatus(status);
        }
        return result;
    }

    /**
     * @return a copy of the map of statusMap
     */
    public Map<String, ServerData> getServers() {
        return new TreeMap<>(servers);
    }

    /**
     * Return all the stored data of a server
     *
     * @param serverName the name of the server
     * @return the data of a server or null
     */
    public ServerData getServer(final String serverName) {
        return servers.get(serverName);
    }

    /**
     * Returns the stored status of a server
     *
     * @param serverName the name of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN if unable to check, null if error
     */
    public ServerStatus getStatus(final String serverName) {
        ServerData server = servers.get(serverName);
        return (server == null) ? null : server.getStatus();
    }

    /**
     * @return a map with all the statusMap and statuses
     */
    public Map<String, ServerStatus> getStatusMap() {
        return new TreeMap<>(servers
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getStatus()
                )));
    }

    /**
     * @return the list of server names
     */
    public List<String> getServerNames() {
        return new ArrayList<>(servers.keySet());
    }

    /**
     * Internal function to clear the storage in unit tests
     */
    void clearStorage() {
        servers.clear();
    }
}
