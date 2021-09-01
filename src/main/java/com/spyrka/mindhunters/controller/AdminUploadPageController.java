package com.spyrka.mindhunters.controller;

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

/**
 * Displays admin page for database back up upload
 */
@Controller
public class AdminUploadPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUploadPageController.class.getName());

    @GetMapping("/admin/page")
    public String uploadPageView(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        Map<String, Object> dataModel = new HashMap<>();
        String name = req.getParameter("name");

        if (name == null || name.isEmpty()) {
            name = "Stranger";
        }
        dataModel.put("name", name.toUpperCase());

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        model.addAllAttributes(dataModel);
        LOGGER.debug("Accessed admin upload page");
        return "adminUploadPage";
    }
}