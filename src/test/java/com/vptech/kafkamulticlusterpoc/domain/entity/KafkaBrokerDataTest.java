package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * Unit test for KafkaBrokerData entity
 */
@SpringBootTest
public class KafkaBrokerDataTest {

    @Test
    public void testConstructorSetsServerType() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();

        Assertions.assertEquals(ServerType.KAFKA_BROKER, kafkaBrokerData.getType());
    }

    @Test
    public void testSetTypeCantChangeTheServerType() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();
        kafkaBrokerData.setType(ServerType.ZOOKEEPER);

        Assertions.assertEquals(ServerType.KAFKA_BROKER, kafkaBrokerData.getType());
    }

    @Test
    public void testBrokerIdIsByDefault0() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();

        Assertions.assertEquals(0, kafkaBrokerData.getBrokerId());
    }

    @Test
    public void testSetBrokerIdChangesTheBrokerId() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();
        kafkaBrokerData.setBrokerId(11);

        Assertions.assertEquals(11, kafkaBrokerData.getBrokerId());
    }

    @Test
    public void testSetServerDataChangesTheBrokerId() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();
        kafkaBrokerData.setServerData(Collections.singletonMap("broker.id", "98"));

        Assertions.assertEquals(98, kafkaBrokerData.getBrokerId());
    }

    @Test
    public void testGetServerDataRetrievesTheBrokerId() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();

        Assertions.assertTrue(kafkaBrokerData.getServerData().containsKey("broker.id"));
    }

    @Test
    public void testBrokerRackIsByDefaultEmpty() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();

        Assertions.assertEquals("", kafkaBrokerData.getBrokerRack());
    }

    @Test
    public void testSetBrokerRackChangesTheBrokerRack() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();
        kafkaBrokerData.setBrokerRack("_rack1_");

        Assertions.assertEquals("_rack1_", kafkaBrokerData.getBrokerRack());
    }

    @Test
    public void testSetServerDataChangesTheBrokerRack() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();
        kafkaBrokerData.setServerData(Collections.singletonMap("broker.rack", "_rack9_"));

        Assertions.assertEquals("_rack9_", kafkaBrokerData.getBrokerRack());
    }

    @Test
    public void testGetServerDataRetrievesTheBrokerRack() {

        KafkaBrokerData kafkaBrokerData = new KafkaBrokerData();

        Assertions.assertTrue(kafkaBrokerData.getServerData().containsKey("broker.rack"));
    }
}
