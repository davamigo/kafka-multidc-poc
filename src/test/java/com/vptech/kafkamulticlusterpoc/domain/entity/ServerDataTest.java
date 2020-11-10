package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Unit test for ServerData entity
 */
@SpringBootTest
public class ServerDataTest {

    @Test
    public void testObjectIsCreatedWithEmptyValues() {

        ServerData serverData = new ServerData();

        Assertions.assertEquals(ServerLocation.NULL, serverData.getLocation());
        Assertions.assertEquals(ServerType.NULL, serverData.getType());
        Assertions.assertEquals(ServerStatus.UNKNOWN, serverData.getStatus());
        Assertions.assertEquals("", serverData.getName());
        Assertions.assertEquals("", serverData.getAddress());
        Assertions.assertEquals(0, serverData.getPort());
    }

    @Test
    public void testSetLocationSetsTheLocation() {

        ServerData serverData = new ServerData();
        serverData.setLocation(ServerLocation.DOCKER);

        Assertions.assertEquals(ServerLocation.DOCKER, serverData.getLocation());
    }

    @Test
    public void testSetTypeSetsTheType() {

        ServerData serverData = new ServerData();
        serverData.setType(ServerType.KAFKA_BROKER);

        Assertions.assertEquals(ServerType.KAFKA_BROKER, serverData.getType());
    }

    @Test
    public void testSetStatusSetsTheStatus() {

        ServerData serverData = new ServerData();
        serverData.setStatus(ServerStatus.DOWN);

        Assertions.assertEquals(ServerStatus.DOWN, serverData.getStatus());
    }

    @Test
    public void testSetNameSetsTheName() {

        ServerData serverData = new ServerData();
        serverData.setName("_some_name_");

        Assertions.assertEquals("_some_name_", serverData.getName());
    }

    @Test
    public void testSetAddressSetsTheAddress() {

        ServerData serverData = new ServerData();
        serverData.setAddress("_some_address_");

        Assertions.assertEquals("_some_address_", serverData.getAddress());
    }

    @Test
    public void testSetPortSetsThePort() {

        ServerData serverData = new ServerData();
        serverData.setPort(1001);

        Assertions.assertEquals(1001, serverData.getPort());
    }

    @Test
    public void testSetServerDataSetsTheLocation() {

        ServerData serverData = new ServerData();
        serverData.setServerData(Collections.singletonMap("location", "DOCKER"));

        Assertions.assertEquals(ServerLocation.DOCKER, serverData.getLocation());
    }

    @Test
    public void testSetServerDataSetsTheType() {

        ServerData serverData = new ServerData();
        serverData.setServerData(Collections.singletonMap("type", "ZOOKEEPER"));

        Assertions.assertEquals(ServerType.ZOOKEEPER, serverData.getType());
    }

    @Test
    public void testSetServerDataSetsTheStatus() {

        ServerData serverData = new ServerData();
        serverData.setServerData(Collections.singletonMap("status", "UP"));

        Assertions.assertEquals(ServerStatus.UP, serverData.getStatus());
    }

    @Test
    public void testSetServerDataSetsTheName() {

        ServerData serverData = new ServerData();
        serverData.setServerData(Collections.singletonMap("name", "_some_name_"));

        Assertions.assertEquals("_some_name_", serverData.getName());
    }

    @Test
    public void testSetServerDataSetsTheAddress() {

        ServerData serverData = new ServerData();
        serverData.setServerData(Collections.singletonMap("address", "_some_address_"));

        Assertions.assertEquals("_some_address_", serverData.getAddress());
    }

    @Test
    public void testSetServerDataSetsThePort() {

        ServerData serverData = new ServerData();
        serverData.setServerData(Collections.singletonMap("port", "2001"));

        Assertions.assertEquals(2001, serverData.getPort());
    }

    @Test
    public void testGetServerDataGetsAllTheFields() {

        ServerData serverData = new ServerData();
        serverData.setLocation(ServerLocation.DOCKER);
        serverData.setType(ServerType.KAFKA_BROKER);
        serverData.setStatus(ServerStatus.DOWN);
        serverData.setName("_someone_");
        serverData.setAddress("_somewhere_");
        serverData.setPort(3001);

        Map<String, String> expected = Stream.of(
                new AbstractMap.SimpleEntry<>("location", "DOCKER"),
                new AbstractMap.SimpleEntry<>("type", "KAFKA_BROKER"),
                new AbstractMap.SimpleEntry<>("status", "DOWN"),
                new AbstractMap.SimpleEntry<>("name", "_someone_"),
                new AbstractMap.SimpleEntry<>("address", "_somewhere_"),
                new AbstractMap.SimpleEntry<>("port", "3001")
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assertions.assertEquals(expected, serverData.getServerData());
    }

    @Test
    public void testGetServerDataWorksWithEmptyObject() {

        ServerData serverData = new ServerData();

        Map<String, String> expected = Stream.of(
                new AbstractMap.SimpleEntry<>("location", "NULL"),
                new AbstractMap.SimpleEntry<>("type", "NULL"),
                new AbstractMap.SimpleEntry<>("status", "UNKNOWN"),
                new AbstractMap.SimpleEntry<>("name", ""),
                new AbstractMap.SimpleEntry<>("address", ""),
                new AbstractMap.SimpleEntry<>("port", "0")
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assertions.assertEquals(expected, serverData.getServerData());
    }

    @Test
    public void testGetExistingFieldRetrievesTheValue() {

        ServerData serverData = new ServerData();
        serverData.setField("_a_field_", "_some_value_");

        final String expected = "_some_value_";

        Assertions.assertEquals(expected, serverData.getField("_a_field_", "_default_"));
    }

    @Test
    public void testGetNonExistingFieldRetrievesTheDefaultValue() {

        ServerData serverData = new ServerData();

        final String expected = "_default_";

        Assertions.assertEquals(expected, serverData.getField("_unknown_", "_default_"));
    }
}
