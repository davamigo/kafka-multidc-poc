package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerLocation;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for PrimaryServerStatusChecker service
 *
 * @author david.amigo
 */
@SpringBootTest
public class PrimaryServerStatusCheckerTest {

    @Test
    public void testConstructorStoresTheServiceList() {
        PrimaryServerStatusChecker checker = new PrimaryServerStatusChecker(Arrays.asList(
                new ServerStatusCheckerMock(),
                new ServerStatusCheckerMock()
        ));

        Assertions.assertEquals(2, checker.services.size());
    }

    @Test
    public void testConstructorWorkWithAnEmptyList() {
        PrimaryServerStatusChecker checker = new PrimaryServerStatusChecker(Collections.emptyList());

        Assertions.assertEquals(0, checker.services.size());
    }

    @Test
    public void testValidLocationsReturnAnEmptyList() {
        ServerStatusChecker checker = new PrimaryServerStatusChecker(Arrays.asList(
                new ServerStatusCheckerMock(),
                new ServerStatusCheckerMock()
        ));

        Assertions.assertTrue(checker.validLocations().isEmpty());
    }

    @Test
    public void testCheckCallsValidLocationsOfEachMockedService() {
        ServerStatusCheckerMock mock1 = new ServerStatusCheckerMock();
        ServerStatusCheckerMock mock2 = new ServerStatusCheckerMock();
        PrimaryServerStatusChecker checker = new PrimaryServerStatusChecker(Arrays.asList(mock1, mock2));
        checker.check((new ServerData()).setLocation(ServerLocation.NULL));

        Assertions.assertEquals(1, mock1.numCallsValidLocation);
        Assertions.assertEquals(1, mock2.numCallsValidLocation);
    }

    @Test
    public void testCheckCallsCheckForTheOnlyMatchingService() {
        ServerStatusCheckerMock mock1 = new ServerStatusCheckerMock(ServerLocation.NULL);
        ServerStatusCheckerMock mock2 = new ServerStatusCheckerMock(ServerLocation.DOCKER);
        PrimaryServerStatusChecker checker = new PrimaryServerStatusChecker(Arrays.asList(mock1, mock2));
        checker.check((new ServerData()).setLocation(ServerLocation.DOCKER));

        Assertions.assertEquals(0, mock1.numCallsCheck);
        Assertions.assertEquals(1, mock2.numCallsCheck);
    }

    @Test
    public void testCheckCallsCheckOnlyForTheFirstMatchingService() {
        ServerStatusCheckerMock mock1 = new ServerStatusCheckerMock(ServerLocation.DOCKER);
        ServerStatusCheckerMock mock2 = new ServerStatusCheckerMock(ServerLocation.NETWORK);
        ServerStatusCheckerMock mock3 = new ServerStatusCheckerMock(ServerLocation.NETWORK);
        PrimaryServerStatusChecker checker = new PrimaryServerStatusChecker(Arrays.asList(mock1, mock2, mock3));
        checker.check((new ServerData()).setLocation(ServerLocation.NETWORK));

        Assertions.assertEquals(0, mock1.numCallsCheck);
        Assertions.assertEquals(1, mock2.numCallsCheck);
        Assertions.assertEquals(0, mock3.numCallsCheck);
    }

    @Test
    public void testCheckReturnTheStatusOfTheMatchingService() {
        ServerStatusCheckerMock mock1 = new ServerStatusCheckerMock(ServerLocation.NULL, ServerStatus.UNKNOWN);
        ServerStatusCheckerMock mock2 = new ServerStatusCheckerMock(ServerLocation.DOCKER, ServerStatus.DOWN);
        ServerStatusCheckerMock mock3 = new ServerStatusCheckerMock(ServerLocation.NETWORK, ServerStatus.UP);
        PrimaryServerStatusChecker checker = new PrimaryServerStatusChecker(Arrays.asList(mock1, mock2, mock3));
        ServerStatus result = checker.check((new ServerData()).setLocation(ServerLocation.DOCKER));

        Assertions.assertEquals(ServerStatus.DOWN, result);
    }

    private static class ServerStatusCheckerMock implements ServerStatusChecker {

        List<ServerLocation> serverLocations;
        ServerStatus serverStatus;
        int numCallsValidLocation;
        int numCallsCheck;

        public ServerStatusCheckerMock() {
            this.serverLocations = Collections.emptyList();
            this.serverStatus = ServerStatus.UNKNOWN;
            this.numCallsValidLocation = 0;
            this.numCallsCheck = 0;
        }

        public ServerStatusCheckerMock(ServerLocation location) {
            this.serverLocations = Collections.singletonList(location);
            this.serverStatus = ServerStatus.UNKNOWN;
            this.numCallsValidLocation = 0;
            this.numCallsCheck = 0;
        }

        public ServerStatusCheckerMock(ServerLocation location, ServerStatus status) {
            this.serverLocations = Collections.singletonList(location);
            this.serverStatus = status;
            this.numCallsValidLocation = 0;
            this.numCallsCheck = 0;
        }

        @Override
        public List<ServerLocation> validLocations() {
            ++numCallsValidLocation;
            return serverLocations;
        }

        @Override
        public ServerStatus check(ServerData server) {
            ++numCallsCheck;
            return serverStatus;
        }
    }
}
