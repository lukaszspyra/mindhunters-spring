package com.spyrka.mindhunters.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping("/error")
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class.getName());

    @GetMapping
    public String doGet(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        LOGGER.error("Bad request: {}", req.getPathInfo());
        return "error";
    }
}
