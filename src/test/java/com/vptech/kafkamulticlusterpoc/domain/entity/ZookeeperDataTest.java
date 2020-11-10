package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * Unit test for ZookeeperData entity
 */
@SpringBootTest
public class ZookeeperDataTest {

    @Test
    public void testConstructorSetsServerType() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertEquals(ServerType.ZOOKEEPER, zookeeperData.getType());
    }

    @Test
    public void testSetTypeCantChangeTheServerType() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setType(ServerType.KAFKA_BROKER);

        Assertions.assertEquals(ServerType.ZOOKEEPER, zookeeperData.getType());
    }

    @Test
    public void testZookeeperIdIsByDefault0() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertEquals(0, zookeeperData.getZookeeperId());
    }

    @Test
    public void testSetZookeeperIdChangesTheZookeeperId() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setZookeeperId(33);

        Assertions.assertEquals(33, zookeeperData.getZookeeperId());
    }

    @Test
    public void testSetServerDataChangesTheZookeeperId() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setServerData(Collections.singletonMap("zookeeper.id", "54"));

        Assertions.assertEquals(54, zookeeperData.getZookeeperId());
    }

    @Test
    public void testGetServerDataRetrievesTheZookeeperId() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertTrue(zookeeperData.getServerData().containsKey("zookeeper.id"));
    }
}
