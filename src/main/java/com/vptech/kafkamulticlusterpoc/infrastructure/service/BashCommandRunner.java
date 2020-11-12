package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Service to run a bash command and return the result
 *
 * @author david.amigo
 */
@Service
public class BashCommandRunner {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BashCommandRunner.class);

    /**
     * Executes a bash command
     *
     * @param command the command to execute
     * @return an string containing the result of the execution
     */
    public String runCommand(final String command) {
        return runCommand(command, 15, TimeUnit.SECONDS);
    }

    /**
     * Executes a bash command defining the timeout
     *
     * @param command the command to execute
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return an string containing the result of the execution
     */
    public String runCommand(final String command, long timeout, TimeUnit unit) {

        LOGGER.debug("BashCommandRunner - executing: " + command);

        try {
            Process process = Runtime.getRuntime().exec(command);
            boolean success = process.waitFor(timeout, unit);
            if (!success) {
                LOGGER.debug("BashCommandRunner - timeout after " + unit.toMillis(timeout) + "ms.");
                return "";
            }

            int exitCode = process.exitValue();
            LOGGER.debug("BashCommandRunner - exit code: " + exitCode);

            String output;
            if (0 == exitCode) {
                output = readStream(process.getInputStream());
                LOGGER.debug("BashCommandRunner - output: " + (output.isEmpty() ? "<empty>" : output.replace("\n", "\\n")));
            } else {
                output = readStream(process.getErrorStream());
                LOGGER.error("BashCommandRunner - error: " + (output.isEmpty() ? "<empty>" : output.replace("\n", "\\n")));
            }
            return output;

        } catch (IOException | InterruptedException exc) {
            LOGGER.error("BashCommandRunner - exception: " + exc.getMessage());
            exc.printStackTrace();
            return exc.getMessage();
        }
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
                if (result.length() > 1) {
                    result.append("\n");
                }
                result.append(line);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return result.toString();
    }
}
