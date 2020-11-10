package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerStatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Service to check the status of a container running in docker
 *
 * @author david.amigo
 */
@Service
public class DockerContainerStatusChecker implements ServerStatusChecker {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DockerContainerStatusChecker.class);

    /**
     * Checks and returns the status of a server
     *
     * @param server the name of the server
     * @return UP if the server is UP, DOWN if the server is down, UNKNOWN in case of unable to check
     */
    @Override
    public ServerStatus check(final String server) {
        ServerStatus result = ServerStatus.UNKNOWN;

        try {
            String command = String.format("docker ps --filter name=%s --format {{.Names}} ", server);
            LOGGER.debug("Bash command: " + command);

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            LOGGER.debug("Bash command exit code: " + exitCode);

            if (0 == exitCode) {
                String output = readStream(process.getInputStream());
                LOGGER.debug("Bash command output: " + (output.isEmpty() ? "<empty>" : output));
                result = (output.isEmpty()) ? ServerStatus.DOWN : ServerStatus.UP;
            } else {
                String output = readStream(process.getErrorStream());
                LOGGER.debug("Bash command error: " + output);
            }
        } catch (IOException | InterruptedException exc) {
            exc.printStackTrace();
        }
        return result;
    }

    /**
     * Reads an input stream
     *
     * @param stream the input stream
     * @return the read lines
     */
    private String readStream(InputStream stream) {

        StringBuilder result = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream)
        );

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return result.toString();
    }
}
