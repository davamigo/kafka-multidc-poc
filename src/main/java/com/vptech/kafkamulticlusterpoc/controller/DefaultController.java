package com.vptech.kafkamulticlusterpoc.controller;

import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerStatus;
import com.vptech.kafkamulticlusterpoc.domain.service.ServerDataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * Logger object
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultController.class);

    /**
     * Server data storage service
     */
    private final ServerDataStorage storage;

    /**
     * Autowired constructor
     *
     * @param storage the server data storage service
     */
    @Autowired
    public DefaultController(ServerDataStorage storage) {
        this.storage = storage;
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

        Map<String, ServerStatus> statusMap = storage.getStatusMap();
        Map<String, ServerData> servers = storage.getServers();

        final ModelAndView mav = new ModelAndView("default/homepage");
        mav.addObject("statusMap", statusMap);
        mav.addObject("servers", servers);
        return mav;
    }
}
