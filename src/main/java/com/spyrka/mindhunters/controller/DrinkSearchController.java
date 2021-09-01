package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.service.AdultVerificationService;
import com.spyrka.mindhunters.service.SearchTypeService;
import com.spyrka.mindhunters.service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DrinkSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkSearchController.class.getName());

    @Autowired
    private SearchTypeService searchTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdultVerificationService adultVerificationService;

    @GetMapping("/search")
    protected String search(Model model, HttpServletRequest req,
                           HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        Map<String, Object> dataModel = searchTypeService.listViewSearchType(req);

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        adultVerificationService.verifyAge18(req, resp, contextHolder);
        adultVerificationService.setAdultFromCookies(req, contextHolder);

        if (contextHolder.getADULT() != null) {
            dataModel.put("adult", contextHolder.getADULT());
        }

        model.addAllAttributes(dataModel);
        return "recipeSearchList";
    }


    @PostMapping("/search")
    protected void addToFavourites(HttpServletRequest req, HttpServletResponse resp) {
        String drinkId = req.getParameter("drinkId");
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();
        if (email != null && !email.isEmpty()) {
            userService.updateUserFavouriteDrinks(email, drinkId);
        }
    }

}