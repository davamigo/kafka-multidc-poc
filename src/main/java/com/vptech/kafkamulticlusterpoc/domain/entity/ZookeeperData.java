package com.vptech.kafkamulticlusterpoc.domain.entity;

/**
 * Entity for managing the data of a Zookeeper server
 *
 * @author david.amigo
 */
public class ZookeeperData extends ServerData {

    /**
     * Constants for the field names
     */
    public static final String FIELD_ZOOKEEPER_ID = "zookeeper.id";

    /**
     * Default constructor
     */
    public ZookeeperData() {
        super();
        this.setType(ServerType.ZOOKEEPER);
        this.setZookeeperId(0);
    }

    /**
     * @param type the type of server: ZOOKEEPER
     * @return this
     */
    @Override
    public ServerData setType(ServerType type) {
        return super.setType(ServerType.ZOOKEEPER);
    }

    /**
     * @param zookeeperID the id of the zookeeper server
     * @return this
     */
    public ZookeeperData setZookeeperId(int zookeeperID) {
        this.setField(FIELD_ZOOKEEPER_ID, String.valueOf(zookeeperID));
        return this;
    }

    /**
     * @return the id of the zookeeper server
     */
    public int getZookeeperId() {
        return Integer.parseInt(getField(FIELD_ZOOKEEPER_ID, "0"));
    }
}
