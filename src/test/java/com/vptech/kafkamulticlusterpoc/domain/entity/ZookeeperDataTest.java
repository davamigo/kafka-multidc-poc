package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for ZookeeperData entity
 *
 * @author david.amigo
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

    @Test
    public void testBrokerIdsIsEmptyByDefault() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertEquals("[]", zookeeperData.getBrokerIds());
    }

    @Test
    public void testSetBrokerIdsChangesTheBrokerIds() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setBrokerIds("[1, 2]");

        Assertions.assertEquals("[1, 2]", zookeeperData.getBrokerIds());
    }

    @Test
    public void testSetServerDataChangesTheBrokerIds() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setServerData(Collections.singletonMap("broker.ids", "[2, 4]"));

        Assertions.assertEquals("[2, 4]", zookeeperData.getBrokerIds());
    }

    @Test
    public void testGetServerDataRetrievesTheBrokerIds() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertTrue(zookeeperData.getServerData().containsKey("broker.ids"));
    }

    @Test
    public void testBrokerDataReturnDefaultValue() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertEquals("{}", zookeeperData.getBrokerData(3));
    }

    @Test
    public void testSetBrokerDataChangesTheBrokerData() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setBrokerData(2, "{ \"bar\": \"baz\" }");

        Assertions.assertEquals("{ \"bar\": \"baz\" }", zookeeperData.getBrokerData(2));
    }

    @Test
    public void testSetServerDataChangesTheBrokerData() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setServerData(Collections.singletonMap("broker.data.5", "{ \"foo\": \"bar\" }"));

        Assertions.assertEquals("{ \"foo\": \"bar\" }", zookeeperData.getBrokerData(5));
    }

    @Test
    public void testGetServerDataDoesntRetrieveTheBrokerDataByDefault() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertFalse(zookeeperData.getServerData().containsKey("broker.data.1"));
    }

    @Test
    public void testGetServerDataRetrievesTheBrokerDataWhenSet() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setBrokerData(9, "{ \"baz\": \"foo\" }");

        Assertions.assertTrue(zookeeperData.getServerData().containsKey("broker.data.9"));
    }

    @Test
    public void testGetBrokerIdsListWithNonEmptyJsonArrayReturnsNonEmptyList() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setBrokerIds("[3, 5, 11]");

        List<Integer> expected = Arrays.asList(3, 5, 11);

        Assertions.assertEquals(expected, zookeeperData.getBrokerIdsList());
    }

    @Test
    public void testGetBrokerIdsListWithEmptyJsonArrayReturnsAnEmptyList() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setBrokerIds("[]");

        Assertions.assertTrue(zookeeperData.getBrokerIdsList().isEmpty());
    }

    @Test
    public void testGetBrokerIdsListWithEmptyObjectReturnsAnEmptyList() {

        ZookeeperData zookeeperData = new ZookeeperData();

        Assertions.assertTrue(zookeeperData.getBrokerIdsList().isEmpty());
    }

    @Test
    public void testGetBrokerIdsListInvalidDataReturnsAnEmptyList() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setBrokerIds("_invalid_");

        Assertions.assertTrue(zookeeperData.getBrokerIdsList().isEmpty());
    }

    @Test
    public void testStaticGetBrokerIdsListWithNonEmptyJsonArrayReturnsTheList() {

        String source = "[1, 45, 278]";
        List<Integer> expected = Arrays.asList(1, 45, 278);

        Assertions.assertEquals(expected, ZookeeperData.getBrokerIdsList(source));
    }

    @Test
    public void testStaticGetBrokerIdsListWithEmptyJsonArrayReturnsAnEmptyList() {

        Assertions.assertTrue(ZookeeperData.getBrokerIdsList("[]").isEmpty());
    }

    @Test
    public void testStaticGetBrokerIdsListWithEmptyStringReturnsAnEmptyList() {

        Assertions.assertTrue(ZookeeperData.getBrokerIdsList("").isEmpty());
    }

    @Test
    public void testStaticGetBrokerIdsListWithInvalidStringReturnsAnEmptyList() {

        Assertions.assertTrue(ZookeeperData.getBrokerIdsList("_inv_").isEmpty());
    }

    @Test
    public void testSetStatusDownClearsExtraFields() {

        ZookeeperData zookeeperData = new ZookeeperData();
        zookeeperData.setZookeeperId(101);
        zookeeperData.setBrokerIds("[2, 8]");
        zookeeperData.setBrokerData(2, "110");
        zookeeperData.setBrokerData(8, "111");
        zookeeperData.setStatus(ServerStatus.DOWN);

        Assertions.assertEquals(0, zookeeperData.getZookeeperId());
        Assertions.assertEquals("[]", zookeeperData.getBrokerIds());
        Assertions.assertEquals("{}", zookeeperData.getBrokerData(2));
        Assertions.assertEquals("{}", zookeeperData.getBrokerData(8));
    }
}
