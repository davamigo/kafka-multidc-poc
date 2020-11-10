package com.vptech.kafkamulticlusterpoc.domain.entity;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base entity for any server data
 *
 * @author david.amigo
 */
public class ServerData {

    /** The location of the server: DOCKER */
    private ServerLocation location;

    /** The type of server: ZOOKEEPER, KAFKA_BROKER */
    private ServerType type;

    /** The status of the server: UP, DOWN, UNKNOWN */
    private ServerStatus status;

    /** The name of the server - depends on the location */
    private String name;

    /** The address of the server */
    private String address;

    /** The port of the server */
    private int port;

    /**
     * Default constructor
     */
    public ServerData() {
        this.location = ServerLocation.NULL;
        this.type = ServerType.NULL;
        this.status = ServerStatus.UNKNOWN;
        this.name = "";
        this.address = "";
        this.port = 0;
    }

    /**
     * Copy constructor
     *
     * @param source the source data
     */
    public ServerData(final ServerData source) {
        this.setServerData(source.getServerData());
    }

    /**
     * @param location the location of the server
     * @return this
     */
    public ServerData setLocation(ServerLocation location) {
        this.location = location;
        return this;
    }

    /**
     * @param type the type of server
     * @return this
     */
    public ServerData setType(ServerType type) {
        this.type = type;
        return this;
    }

    /**
     * @param status the status of the server
     * @return this
     */
    public ServerData setStatus(ServerStatus status) {
        this.status = status;
        return this;
    }

    /**
     * @param name the name of the server
     * @return this
     */
    public ServerData setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param address the address of the server
     * @return this
     */
    public ServerData setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * @param port the port of the server
     * @return this
     */
    public ServerData setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Inits all the properties from a map of properties and values
     *
     * @param data the map of properties and values
     * @return this
     */
    public ServerData setServerData (Map<String, String> data) {

        String location = data.getOrDefault("location", "NULL");
        this.location = ServerLocation.valueOf(location);

        String type = data.getOrDefault("type", "NULL");
        this.type = ServerType.valueOf(type);

        String status = data.getOrDefault("status", "UNKNOWN");
        this.status = ServerStatus.valueOf(status);

        this.name = data.getOrDefault("name", "");
        this.address = data.getOrDefault("address", "");

        String port = data.getOrDefault("port", "0");
        this.port = Integer.parseInt(port);

        return this;
    }

    /**
     * @return the location of the server
     */
    public ServerLocation getLocation() {
        return location;
    }

    /**
     * @return the type of server
     */
    public ServerType getType() {
        return type;
    }

    /**
     * @return the status of the server
     */
    public ServerStatus getStatus() {
        return status;
    }

    /**
     * @return the name of the server
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address of the server
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the port of the server
     */
    public int getPort() {
        return port;
    }

    /**
     * Get all the data as a map of properties and values
     *
     * @return a map of properties and values
     */
    public Map<String, String> getServerData() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("location", this.location.toString()),
                new AbstractMap.SimpleEntry<>("type", this.type.toString()),
                new AbstractMap.SimpleEntry<>("status", this.status.toString()),
                new AbstractMap.SimpleEntry<>("name", this.name),
                new AbstractMap.SimpleEntry<>("address", this.address),
                new AbstractMap.SimpleEntry<>("port", String.valueOf(this.port))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
