package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.services.SearchTypeService;
import com.spyrka.mindhunters.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DrinkSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkSearchController.class.getName());

    @Autowired
    private SearchTypeService searchTypeService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    protected String doGet(Model dataModel, HttpServletRequest req,
                           HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        dataModel.addAllAttributes(searchTypeService.listViewSearchType(req));

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.addAttribute("name", contextHolder.getName());
        dataModel.addAttribute("role", contextHolder.getRole());


        verifyAge18(req, resp, contextHolder);

        setAdultFromCookies(req, contextHolder);


        if (contextHolder.getADULT() != null) {
            dataModel.addAttribute("adult", contextHolder.getADULT());
        }

        return "recipeSearchList";
    }

    private void verifyAge18(HttpServletRequest req, HttpServletResponse resp, ContextHolder contextHolder) {
        String adult = req.getParameter("adult");
        String age18 = req.getParameter("age18");

        if (adult != null) {

            switch (adult) {

                case "true":
                    contextHolder.setADULT(adult);

                    if (age18 != null) {

                        createAdultCookie(resp, "true");

                    }
                    break;

                case "false":

                    contextHolder.setADULT(adult);

                    if (age18 != null) {

                        createAdultCookie(resp, "false");

                    }

                    break;
            }
        }
    }

    private void createAdultCookie(HttpServletResponse resp, String value) {
        Cookie cookie = new Cookie("age18", value);
        cookie.setMaxAge(60 * 60 * 24);
        resp.addCookie(cookie);
    }

    private void setAdultFromCookies(HttpServletRequest req, ContextHolder contextHolder) {
        Cookie[] c = req.getCookies();
        if (c != null) {

            final List<Cookie> age18s =
                    Arrays.stream(c).filter(e -> e.getName().equalsIgnoreCase("age18")).collect(Collectors.toList());

            if (!age18s.isEmpty()) {
                contextHolder.setADULT(age18s.get(0).getValue());
            }
        }
    }


    @PostMapping("/search")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String drinkId = req.getParameter("drinkId");

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();

        if (email != null && !email.isEmpty()) {

            userService.saveOrDeleteFavourite(email, drinkId);

        }
    }

}