package com.spyrka.mindhunters.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Check if login system is operational
 */
@Controller
@RequestMapping("/admin/loggers")
public class LoggerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class.getName());

    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        LOGGER.debug("Mindhunters Debug\n");
        LOGGER.info("Mindhunters Info\n");
        LOGGER.warn("Mindhunters Warn\n");
        LOGGER.error("Mindhunters Error\n");
    }
}
