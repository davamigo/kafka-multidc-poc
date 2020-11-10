package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * Unit test for ServerDataStorage service
 */
@SpringBootTest
public class ServerDataStorageTest {

    @Test
    public void testGetServerWithEmptyStorageReturnsNull() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();

        Assertions.assertNull(storage.getServer("101"));
    }

    @Test
    public void testGetServerAfterAddingAnEmptyServerReturnsTheServer() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();
        storage.addServer("201");

        ServerData result = storage.getServer("201");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("201", result.getName());
    }

    @Test
    public void testGetServerAfterAddingNonEmptyServerReturnsTheServer() {

        ServerData server = (new ServerData()).setName("301");
        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();
        storage.addServer(server);

        ServerData result = storage.getServer("301");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(server.getServerData(), result.getServerData());
    }

    @Test
    public void testSetStatusOfNonExistingServerReturnsNull() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();

        ServerStatus result = storage.setStatus("401", ServerStatus.DOWN);

        Assertions.assertNull(result);
    }

    @Test
    public void testSetStatusOfExistingServerReturnsThePreviousStatus() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();
        storage.addServer((new ServerData()).setName("501"));

        ServerStatus result = storage.setStatus("501", ServerStatus.UP);

        Assertions.assertEquals(ServerStatus.UNKNOWN, result);
    }

    @Test
    public void testGetStatusOfNonExistingServerReturnsNull() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();

        ServerStatus result = storage.getStatus("601");

        Assertions.assertNull(result);
    }

    @Test
    public void testGetStatusOfExistingServerReturnsTheStatus() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();
        storage.addServer((new ServerData()).setName("701").setStatus(ServerStatus.DOWN));

        ServerStatus result = storage.getStatus("701");

        Assertions.assertEquals(ServerStatus.DOWN, result);
    }

    @Test
    public void testGetServerNamesWithEmptyStorageReturnsAnEmptyList() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();

        List<String> result = storage.getServerNames();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetServerNamesWithNonEmptyStorageReturnsTheServersList() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();
        storage.addServer("801");
        storage.addServer("802");
        storage.addServer("803");

        List<String> result = storage.getServerNames();

        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void testGetAllStatusesWithEmptyStorageReturnsAnEmptyMsp() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();

        Map<String, ServerStatus> result = storage.getAllStatuses();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllStatusesWithNonEmptyStorageReturnsMapOfStatus() {

        ServerDataStorage storage = new ServerDataStorage();
        storage.clearStorage();
        storage.addServer("901");
        storage.addServer("902");
        storage.addServer("903");

        Map<String, ServerStatus> result = storage.getAllStatuses();

        Assertions.assertEquals(3, result.size());
    }
}
