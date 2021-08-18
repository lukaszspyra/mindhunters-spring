package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.services.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementController.class.getName());

    @Autowired
    private AdminUserService adminUserService;


    @GetMapping
    public String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());
        dataModel.put("users", adminUserService.showUsers());
        model.addAllAttributes(dataModel);

        LOGGER.debug("List of users sent to admin panel adminUserManagement");

        return "adminUserManagement";
    }

    @PostMapping
    public String doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        String userId = req.getParameter("u");
        String adminId = req.getParameter("a");

        if (userId == null || userId.isBlank()) {
            adminUserService.removeAdminRole(adminId);
        }
        adminUserService.setAdminRole(userId);
        dataModel.put("users", adminUserService.showUsers());
        model.addAllAttributes(dataModel);

        LOGGER.debug("Change role of user to admin and sent list of users");

        return "adminUserManagement";
    }
}
