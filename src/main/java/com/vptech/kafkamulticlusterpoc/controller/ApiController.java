package com.vptech.kafkamulticlusterpoc.controller;

import com.vptech.kafkamulticlusterpoc.domain.entity.TopicStatistics;
import com.vptech.kafkamulticlusterpoc.domain.service.TopicStatisticsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API controller
 *
 * Base Path: /api
 *
 * @author david.amigo
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    /** Logger object */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    /** The topic statistics storage service */
    private final TopicStatisticsStorage topicsStatsStorage;

    /**
     * Autowired constructor
     *
     * @param topicsStatsStorage the topic statistics storage service
     */
    @Autowired
    public ApiController(TopicStatisticsStorage topicsStatsStorage) {
        this.topicsStatsStorage = topicsStatsStorage;
    }

    /**
     * Entrypoint to get the list of topics
     *
     * Path: /api/topic/names
     *
     * @return a list of topics
     */
    @GetMapping(value = "/topic/names")
    public ResponseEntity<List<String>> topicsAction() {
        LOGGER.debug("ApiController.topicsAction()");
        return new ResponseEntity<>(topicsStatsStorage.getAllTopics(), HttpStatus.OK);
    }

    /**
     * Entrypoint to get the stats of a topic
     *
     * Path: /api/topic/{name}/stats
     * Var: name - the name of the topic
     *
     * @return the stats of a topic
     */
    @GetMapping(value = "/topic/{name}/stats")
    public ResponseEntity<TopicStatistics> topicStatsAction(
            @PathVariable("name") String name
    ) {
        LOGGER.debug("ApiController.topicStatsAction(" + name + ")");
        TopicStatistics starts = topicsStatsStorage.getTopicStatistics(name);
        if (null == starts) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(starts, HttpStatus.OK);
    }

    /**
     * Entrypoint to get the list of pending messages of a topic
     *
     * Path: /api/topic/{name}/pending
     * Var: name - the name of the topic
     *
     * @return the list of pending messages of a topic
     */
    @GetMapping(value = "/topic/{name}/pending")
    public ResponseEntity<List<String>> topicPendingMessagesAction(
            @PathVariable("name") String name
    ) {
        LOGGER.debug("ApiController.topicPendingMessagesAction(" + name + ")");
        TopicStatistics starts = topicsStatsStorage.getTopicStatistics(name);
        if (null == starts) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<String> pendingMessages = starts.getPendingMessages();
        return new ResponseEntity<>(pendingMessages, HttpStatus.OK);
    }

    /**
     * Entrypoint to get the list of out of sequence messages of a topic
     *
     * Path: /api/topic/{name}/outofseq
     * Var: name - the name of the topic
     *
     * @return the list of out of sequence messages of a topic
     */
    @GetMapping(value = "/topic/{name}/outofseq")
    public ResponseEntity<List<String>> topicOutOfSeqMessagesAction(
            @PathVariable("name") String name
    ) {
        LOGGER.debug("ApiController.topicOutOfSeqMessagesAction(" + name + ")");
        TopicStatistics starts = topicsStatsStorage.getTopicStatistics(name);
        if (null == starts) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<String> outOfSeqMessages = starts.getOutOfSeqMessages();
        return new ResponseEntity<>(outOfSeqMessages, HttpStatus.OK);
    }

    /**
     * Entrypoint to get the list of messages unable to produce of a topic
     *
     * Path: /api/topic/{name}/errors
     * Var: name - the name of the topic
     *
     * @return the list of unable to produce messages of a topic
     */
    @GetMapping(value = "/topic/{name}/errors")
    public ResponseEntity<List<String>> topicErrorsMessagesAction(
            @PathVariable("name") String name
    ) {
        LOGGER.debug("ApiController.topicErrorsMessagesAction(" + name + ")");
        TopicStatistics starts = topicsStatsStorage.getTopicStatistics(name);
        if (null == starts) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<String> errorMessages = starts.getUnableToProduceMessages();
        return new ResponseEntity<>(errorMessages, HttpStatus.OK);
    }
}
