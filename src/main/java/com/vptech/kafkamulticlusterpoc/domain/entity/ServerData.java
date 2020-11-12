package com.vptech.kafkamulticlusterpoc.domain.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Base entity for any server data
 *
 * @author david.amigo
 */
public class ServerData {

    /**
     * Constants for the field names
     */
    public static final String FIELD_LOCATION = "location";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_PORT = "port";

    /**
     * The properties of the server
     */
    private final Map<String, String> properties;

    /**
     * Default constructor
     */
    public ServerData() {
        this.properties = new HashMap<>();
        this.setLocation(ServerLocation.NULL)
                .setType(ServerType.NULL)
                .setStatus(ServerStatus.UNKNOWN)
                .setName("")
                .setAddress("")
                .setPort(0);
    }

    /**
     * Inits all the properties from a map of properties and values
     *
     * @param data the map of properties and values
     * @return this
     */
    public ServerData setServerData (Map<String, String> data) {
        for (String field : data.keySet()) {
            this.setField(field, data.get(field));
        }
        return this;
    }

    /**
     * @param location the location of the server: DOCKER
     * @return this
     */
    public ServerData setLocation(ServerLocation location) {
        this.setField(FIELD_LOCATION, location.toString());
        return this;
    }

    /**
     * @param type the type of server: ZOOKEEPER, KAFKA_BROKER
     * @return this
     */
    public ServerData setType(ServerType type) {
        this.setField(FIELD_TYPE, type.toString());
        return this;
    }

    /**
     * @param status the status of the server: UP, DOWN, UNKNOWN
     * @return this
     */
    public ServerData setStatus(ServerStatus status) {
        this.setField(FIELD_STATUS, status.toString());
        return this;
    }

    /**
     * @param name the name of the server - depends on the location
     * @return this
     */
    public ServerData setName(String name) {
        this.setField(FIELD_NAME, name);
        return this;
    }

    /**
     * @param address the address of the server
     * @return this
     */
    public ServerData setAddress(String address) {
        this.setField(FIELD_ADDRESS, address);
        return this;
    }

    /**
     * @param port the port of the server
     * @return this
     */
    public ServerData setPort(int port) {
        this.setField(FIELD_PORT, String.valueOf(port));
        return this;
    }

    /**
     * Get all the data as a map of properties and values
     *
     * @return a map of properties and values
     */
    public Map<String, String> getServerData() {
        return new TreeMap<>(this.properties);
    }

    /**
     * @return the location of the server
     */
    public ServerLocation getLocation() {
        return ServerLocation.valueOf(getField(FIELD_LOCATION, "NULL"));
    }

    /**
     * @return the type of server
     */
    public ServerType getType() {
        return ServerType.valueOf(getField(FIELD_TYPE, "NULL"));
    }

    /**
     * @return the status of the server
     */
    public ServerStatus getStatus() {
        return ServerStatus.valueOf(getField(FIELD_STATUS, "UNKNOWN"));
    }

    /**
     * @return the name of the server
     */
    public String getName() {
        return getField(FIELD_NAME, "");
    }

    /**
     * @return the address of the server
     */
    public String getAddress() {
        return getField(FIELD_ADDRESS, "");
    }

    /**
     * @return the port of the server
     */
    public int getPort() {
        return Integer.parseInt(getField(FIELD_PORT, "0"));
    }

    /**
     * @param field the name of the field
     * @param value the new value
     */
    synchronized void setField(final String field, final String value) {
        this.properties.put(field, value);
    }

    /**
     * @param field the name of the field
     */
    synchronized void removeField(final String field) {
        this.properties.remove(field);
    }

    /**
     * @param field the name of the field
     * @param defaultValue the default value
     * @return the value of a field
     */
    synchronized String getField(final String field, final String defaultValue) {
        if (!this.properties.containsKey(field)) {
            return defaultValue;
        }
        return this.properties.get(field);
    }
}
