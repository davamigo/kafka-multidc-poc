package com.vptech.kafkamulticlusterpoc.domain.entity;

/**
 * Entity for managing the data of a Kafka broker
 *
 * @author david.amigo
 */
public class KafkaBrokerData extends ServerData {

    /**
     * Constants for the field names
     */
    public static final String FIELD_BROKER_ID = "broker.id";
    public static final String FIELD_BROKER_RACK = "broker.rack";

    /**
     * Default constructor
     */
    public KafkaBrokerData() {
        super();
        this.setType(ServerType.KAFKA_BROKER);
        this.setBrokerId(0);
        this.setBrokerRack("");
    }

    /**
     * @param type the type of server: KAFKA_BROKER
     * @return this
     */
    @Override
    public ServerData setType(ServerType type) {
        return super.setType(ServerType.KAFKA_BROKER);
    }

    /**
     * @param brokerID the id of the kafka broker
     * @return this
     */
    public KafkaBrokerData setBrokerId(int brokerID) {
        this.setField(FIELD_BROKER_ID, String.valueOf(brokerID));
        return this;
    }

    /**
     * @param brokerRack the rack the kafka broker (used to set the DC)
     * @return this
     */
    public KafkaBrokerData setBrokerRack(final String brokerRack) {
        this.setField(FIELD_BROKER_RACK, brokerRack);
        return this;
    }

    /**
     * @return the id of the kafka broker
     */
    public int getBrokerId() {
        return Integer.parseInt(getField(FIELD_BROKER_ID, "0"));
    }

    /**
     * @return the id of the kafka broker
     */
    public String getBrokerRack() {
        return getField(FIELD_BROKER_RACK, "");
    }
}
