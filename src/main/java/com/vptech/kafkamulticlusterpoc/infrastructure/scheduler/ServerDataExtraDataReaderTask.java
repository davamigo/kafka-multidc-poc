package com.vptech.kafkamulticlusterpoc.infrastructure.scheduler;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerExtraDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Scheduled task to get extra data from the servers
 *
 * @author david.amigo
 */
@Component
public class ServerDataExtraDataReaderTask {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStatusCheckerTask.class);

    /** Storage for the server data */
    private final ServerDataStorage storage;

    /** Map of services to read some data from the servers */
    private final List<ServerExtraDataReader> readers;

    /**
     *
     * @param storage the storage for the server data
     * @param readers the map of services to read some data from the servers
     */
    @Autowired
    public ServerDataExtraDataReaderTask(
            ServerDataStorage storage,
            List<ServerExtraDataReader> readers
    ) {
        this.storage = storage;
        this.readers = readers;
    }

    /**
     * Scheduled task get extra data from the servers
     */
    @Scheduled(cron = "${app.config.task.ServerDataExtraDataReaderTask.cron:-}")
    public void checkServerStatusTask() {

        LOGGER.debug("ServerDataExtraDataReaderTask - starting task...");
        for (String serverName : storage.getServerNames()) {

            LOGGER.debug("ServerDataExtraDataReaderTask - Reading extra data from " + serverName + "...");
            ServerData server = storage.getServer(serverName);

            for (ServerExtraDataReader reader : readers) {
                if (reader.isEligible(server)) {
                    reader.readExtraData(server);
                }
            }
        }
        LOGGER.debug("ServerDataExtraDataReaderTask - task finished.");
    }
}
