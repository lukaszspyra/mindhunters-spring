package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.context.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller("/not-found")
public class NoAccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoAccessController.class.getName());

    @GetMapping
    public String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());
        LOGGER.info("Not authorized request: {}", contextHolder.getName());
        model.addAllAttributes(dataModel);
        return "noAccess";
    }
}
