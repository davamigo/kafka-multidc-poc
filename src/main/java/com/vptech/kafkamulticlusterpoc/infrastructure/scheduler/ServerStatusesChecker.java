package com.vptech.kafkamulticlusterpoc.infrastructure.scheduler;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerStatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task to check the status of the servers
 *
 * @author david.amigo
 */
@Component
public class ServerStatusesChecker {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStatusesChecker.class);

    /** Storage for the server data */
    private final ServerDataStorage storage;

    /** Service to check the server status */
    private final ServerStatusChecker checker;

    /**
     * Autowired constructor
     *
     * @param storage the storage for the server statuses
     * @param checker the service to check the server status
     */
    @Autowired
    public ServerStatusesChecker(
            final ServerDataStorage storage,
            final ServerStatusChecker checker
    ) {
        this.storage = storage;
        this.checker = checker;
    }

    /**
     * Scheduled task to check the server status every 5 seconds
     */
    @Scheduled(cron = "${app.config.task.CheckServerStatuses.cron:-}")
    public void checkServerStatuses() {
        LOGGER.debug("Checking the server statuses...");
        for (String server : storage.getServerNames()) {
            LOGGER.debug("Checking server " + server + " status...");
            ServerStatus status = checker.check(server);
            LOGGER.debug("The status of server " + server + " is " + status);
            storage.setStatus(server, status);
        }
    }
}
