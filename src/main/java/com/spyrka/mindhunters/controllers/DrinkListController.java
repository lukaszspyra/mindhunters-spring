package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.dto.CategoryView;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.services.CategoryService;
import com.spyrka.mindhunters.services.DrinkService;
import com.spyrka.mindhunters.services.SearchType;
import com.spyrka.mindhunters.services.UserService;
import com.spyrka.mindhunters.services.validator.UserInputValidator;
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
public class DrinkListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkListController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private UserService userService;

    @GetMapping(
            path = "/list"
    )
    public String doGet(Model model,
                        HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");


        String pageNumberReq = req.getParameter("page");

        int currentPage;

        if (!userInputValidator.validatePageNumber(pageNumberReq)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(pageNumberReq);
        }

        final List<CategoryView> categories = categoryService.findAllCategories();

        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());


        verifyAge18(req, resp, contextHolder);

        setAdultFromCookies(req, contextHolder);


        if (contextHolder.getADULT() != null) {
            dataModel.put("adult", contextHolder.getADULT());
        }


        String email = contextHolder.getEmail();

        Map<String, String[]> searchParam = req.getParameterMap();

        SearchType searchType = drinkService.checkingSearchingCase(searchParam, currentPage);

        int maxPage = searchType.getMaxPage();

        List<FullDrinkView> drinkViewList = searchType.getDrinkViewList();

        String queryName = searchType.getQueryName();

        if (email != null && !email.isEmpty()) {

            List<FullDrinkView> favouritesList = userService.favouritesList(email);

            if (!favouritesList.isEmpty()) {
                List<Object> favouritesListModel = favouritesList.stream()
                        .map(FullDrinkView::getId)
                        .map(aLong -> Integer.parseInt(aLong.toString()))
                        .collect(Collectors.toList());

                dataModel.put("favourites", favouritesListModel);
            }

        }

        String servletPath = req.getServletPath();

        dataModel.put("servletPath", servletPath);
        dataModel.put("categories", categories);
        dataModel.put("maxPageSize", maxPage);
        dataModel.put("queryName", queryName);
        dataModel.put("drinkList", drinkViewList);
        dataModel.put("currentPage", currentPage);

        model.addAllAttributes(dataModel);

        return "recipeList";

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


    @PostMapping(
            path = "/list"
    )
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String drinkId = req.getParameter("drinkId");

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();

        if (email != null && !email.isEmpty()) {

            userService.saveOrDeleteFavourite(email, drinkId);

        }

    }
}
