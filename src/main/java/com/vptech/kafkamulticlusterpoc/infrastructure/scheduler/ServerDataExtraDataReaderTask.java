package com.vptech.kafkamulticlusterpoc.infrastructure.scheduler;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerExtraDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

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

    /** List of services to read extra data from the servers */
    private final List<ServerExtraDataReader> readers;

    /** Array of threads to allow parallelization of data readers */
    private final int maxThreads;
    private final Thread[] threads;

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
        this.maxThreads = 15;
        this.threads = new Thread[this.maxThreads];
    }

    /**
     * Scheduled task get extra data from the servers
     */
    @Scheduled(cron = "${app.config.scheduler.task.ServerDataExtraDataReaderTask.cron:-}")
    public void checkServerStatusTask() {

        StopWatch watch = new StopWatch();
        watch.start();

        LOGGER.debug("ServerDataExtraDataReaderTask - starting task...");
        for (String serverName : storage.getServerNames()) {

            LOGGER.debug("ServerDataExtraDataReaderTask - checking server readers for " + serverName + "...");
            ServerData server = storage.getServer(serverName);

            for (ServerExtraDataReader reader : readers) {
                if (reader.isEligible(server)) {
                    readExtraServerData(reader, server);
                }
            }
        }

        watch.stop();
        LOGGER.debug("ServerDataExtraDataReaderTask - task finished in " + watch.getLastTaskTimeMillis() + "ms.");
    }

    /**
     * Reads extra data of a server in a separate thread
     *
     * @param reader the service to read extra data
     * @param server the current data of the server
     */
    private void readExtraServerData(ServerExtraDataReader reader, ServerData server) {

        boolean done = false;
        while (!done) {
            for (int i = 0; i < maxThreads && !done; i++) {
                if (threads[i] == null
                    || !threads[i].isAlive()) {
                    String threadName = "ServerReader-" + (1 + i);
                    LOGGER.debug("ServerDataExtraDataReaderTask - creating new thread: " + threadName);
                    threads[i] = new Thread(new RunnableReader(reader, server), threadName);
                    threads[i].start();
                    done = true;
                }
            }
            if (!done) {
                try {
                    LOGGER.debug("ServerDataExtraDataReaderTask - waiting for available threads...");
                    Thread.sleep(500);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    /**
     * Runnable class to read extra data from a server in a separate thread
     */
    private static class RunnableReader implements Runnable {
        final ServerExtraDataReader reader;
        final ServerData server;

        public RunnableReader(ServerExtraDataReader reader, ServerData server) {
            this.reader = reader;
            this.server = server;
        }

        @Override
        public void run() {
            reader.readExtraData(server);
        }
    }
}
