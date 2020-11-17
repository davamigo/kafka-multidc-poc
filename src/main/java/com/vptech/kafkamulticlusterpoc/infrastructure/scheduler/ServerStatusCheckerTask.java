package com.vptech.kafkamulticlusterpoc.infrastructure.scheduler;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerStatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Scheduled task to check the status of the servers
 *
 * @author david.amigo
 */
@Component
public class ServerStatusCheckerTask {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStatusCheckerTask.class);

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
    public ServerStatusCheckerTask(
            final ServerDataStorage storage,
            final ServerStatusChecker checker
    ) {
        this.storage = storage;
        this.checker = checker;
    }

    /**
     * Scheduled task to check the server status periodically
     */
    @Scheduled(cron = "${app.config.scheduler.task.CheckServerStatusTask.cron:-}")
    public void checkServerStatusTask() {

        StopWatch watch = new StopWatch();
        watch.start();

        LOGGER.debug("ServerStatusCheckerTask - starting task...");
        for (ServerData server : storage.getServers().values()) {

            LOGGER.debug("ServerStatusCheckerTask - checking server status of " + server.getName() + "...");
            ServerStatus status = checker.check(server);

            LOGGER.debug("ServerStatusCheckerTask - the status of server " + server.getName() + " is " + status.toString());
            storage.setStatus(server.getName(), status);
        }

        watch.stop();
        LOGGER.debug("ServerStatusCheckerTask - task finished in " + watch.getLastTaskTimeMillis() + "ms.");
    }
}
