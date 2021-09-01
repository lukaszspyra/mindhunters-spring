package com.spyrka.mindhunters.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Displays default view for error request
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class.getName());

    @GetMapping("/error")
    public String doGet(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        LOGGER.error("Bad request: {}", req.getRequestURL());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
