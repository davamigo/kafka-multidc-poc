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
    public static final String FIELD_BROKER_IDS = "broker.ids";
    public static final String FIELD_BROKER_DATA = "broker.data.";

    /**
     * Default constructor
     */
    public ZookeeperData() {
        super();
        this.setType(ServerType.ZOOKEEPER);
        this.setZookeeperId(0);
        this.setBrokerIds("[]");
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
     * Sets the value of "zookeeper.id", which is the id of the zookeeper server in tue quorum.
     *
     * @param zookeeperID the id of the zookeeper server
     * @return this
     */
    public ZookeeperData setZookeeperId(int zookeeperID) {
        this.setField(FIELD_ZOOKEEPER_ID, String.valueOf(zookeeperID));
        return this;
    }

    /**
     * Sets the value of "broker.ids", which is the ids of the Kafka brokers connected to the
     * cluster, in JSON format. E.g: [1, 2, 3]
     *
     * @param brokerIds the ids of the brokers connected in JSON format.
     * @return this
     */
    public ZookeeperData setBrokerIds(String brokerIds) {
        this.setField(FIELD_BROKER_IDS, brokerIds);
        return this;
    }

    /**
     * Sets the value of "broker.data.N", where N is the id of the broker, which is the raw data
     * of the Kafka broker read from Zookeeper, in JSON format.
     *
     * @param brokerId   the ID of the Kafka broker in the cluster.
     * @param brokerData the raw data of the broker in JSON format.
     * @return this
     */
    public ZookeeperData setBrokerData(int brokerId, String brokerData) {
        this.setField(FIELD_BROKER_DATA + brokerId , brokerData);
        return this;
    }

    /**
     * Returns the value of "zookeeper.id", which is the id of the zookeeper server in tue quorum.
     *
     * @return the id of the zookeeper server
     */
    public int getZookeeperId() {
        return Integer.parseInt(getField(FIELD_ZOOKEEPER_ID, "0"));
    }

    /**
     * Returns the value of "broker.ids", which is the ids of the Kafka brokers connected to the
     * cluster, in JSON format. E.g: [1, 2, 3]
     *
     * @return the ids of the brokers connected in JSON format
     */
    public String getBrokerIds() {
        return getField(FIELD_BROKER_IDS, "[]");
    }

    /**
     * Returns the value of "broker.data.N", where N is the id of the broker, which is the raw
     * data of the Kafka broker read from Zookeeper, in JSON format.
     *
     * @param brokerId the ID of the Kafka broker in the cluster.
     * @return the raw data of the broker in JSON format.
     */
    public String getBrokerData(int brokerId) {
        return this.getField(FIELD_BROKER_DATA + brokerId , "{}");
    }
}
