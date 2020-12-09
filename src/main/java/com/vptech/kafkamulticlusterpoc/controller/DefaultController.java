package com.vptech.kafkamulticlusterpoc.controller;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.entity.TopicStatistics;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import com.vptech.kafkamulticlusterpoc.domain.service.TopicStatisticsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Default controller
 * Base Path: /
 *
 * @author david.amigo
 */
@Controller
@RequestMapping("/")
public class DefaultController {

    /** Logger object */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultController.class);

    /** The server data storage service */
    private final ServerDataStorage serverDataStorage;

    /** The topic statistics storage service */
    private final TopicStatisticsStorage topicsStatsStorage;

    /**
     * Autowired constructor
     *
     * @param serverDataStorage  the server data storage service
     * @param topicsStatsStorage the topic statistics storage service
     */
    @Autowired
    public DefaultController(
            final ServerDataStorage serverDataStorage,
            final TopicStatisticsStorage topicsStatsStorage
    ) {
        this.serverDataStorage = serverDataStorage;
        this.topicsStatsStorage = topicsStatsStorage;
    }

    /**
     * Default page
     *
     * Path: /
     *
     * @return the HTML of the home page
     */
    @GetMapping("/")
    public ModelAndView defaultAction() {
        LOGGER.debug("DefaultController.defaultAction()");

        final Map<String, ServerStatus> statusMap = serverDataStorage.getStatusMap();
        final Map<String, ServerData> serversMap = serverDataStorage.getServers();
        final List<TopicStatistics> topicList = topicsStatsStorage.getAllTopicStatistics();

        final ModelAndView mav = new ModelAndView("default/default");
        mav.addObject("statusMap", statusMap);
        mav.addObject("serversMap", serversMap);
        mav.addObject("topicList", topicList);
        return mav;
    }

    /**
     * Topics list page
     *
     * Path: /topics
     *
     * @return the HTML of the home page
     */
    @GetMapping("/topics")
    public ModelAndView topicsAction() {
        LOGGER.debug("DefaultController.topicsAction()");

        final List<String> topics = topicsStatsStorage.getAllTopics();

        final ModelAndView mav = new ModelAndView("default/topics");
        mav.addObject("topics", topics);
        return mav;
    }
}
