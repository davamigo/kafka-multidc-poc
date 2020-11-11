package com.vptech.kafkamulticlusterpoc.infrastructure.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        LOGGER.debug("BashCommandRunner - executing: " + command);

        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            LOGGER.debug("BashCommandRunner - exit code: " + exitCode);

            String output;
            if (0 == exitCode) {
                output = readStream(process.getInputStream());
                LOGGER.debug("BashCommandRunner - output: " + (output.isEmpty() ? "<empty>" : output));
            } else {
                output = readStream(process.getErrorStream());
                LOGGER.debug("BashCommandRunner - error: " + output);
            }
            return output;

        } catch (IOException | InterruptedException exc) {
            LOGGER.debug("BashCommandRunner - exception: " + exc.getMessage());
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
                result.append(line);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return result.toString();
    }
}
