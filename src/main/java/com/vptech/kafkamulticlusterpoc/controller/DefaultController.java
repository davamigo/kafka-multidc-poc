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
    private final ServerDataStorage serverData;

    /** The topic statistics storage service */
    private final TopicStatisticsStorage topicStatistics;

    /**
     * Autowired constructor
     *
     * @param serverData      the server data storage service
     * @param topicStatistics the topic statistics storage service
     */
    @Autowired
    public DefaultController(
            final ServerDataStorage serverData,
            final TopicStatisticsStorage topicStatistics
    ) {
        this.serverData = serverData;
        this.topicStatistics = topicStatistics;
    }

    /**
     * Default Homepage
     *
     * Path: /
     * Params: -
     *
     * @return the HTML of the home page
     */
    @GetMapping("/")
    public ModelAndView homepage() {
        LOGGER.debug("DefaultController.homepage");

        Map<String, ServerStatus> statusMap = serverData.getStatusMap();
        Map<String, ServerData> serversMap = serverData.getServers();
        List<TopicStatistics> topicList = topicStatistics.getAllTopicStatistics();

        final ModelAndView mav = new ModelAndView("default/homepage");
        mav.addObject("statusMap", statusMap);
        mav.addObject("serversMap", serversMap);
        mav.addObject("topicList", topicList);
        return mav;
    }
}
