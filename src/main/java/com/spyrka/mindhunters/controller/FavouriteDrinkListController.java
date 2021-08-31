package com.spyrka.mindhunters.controller;

import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.service.UserService;
import com.spyrka.mindhunters.service.validator.UserInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/favourites")
public class FavouriteDrinkListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavouriteDrinkListController.class.getName());

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private UserService userService;

    @GetMapping
    public String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String pageNumberReq = req.getParameter("page");

        int currentPage;

        if (!userInputValidator.validatePageNumber(pageNumberReq)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(pageNumberReq);
        }

        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        String email = contextHolder.getEmail();


        List<FullDrinkView> drinkViewList = userService.favouritesList(email, currentPage);

        int maxPage = userService.countPagesFavouritesList(email);

        dataModel.put("drinkList", drinkViewList);

        List<Integer> favouritesId = new ArrayList<>();

        if (!drinkViewList.isEmpty()) {
            favouritesId = drinkViewList.stream()
                    .map(FullDrinkView::getId)
                    .map(Long::intValue)
                    .collect(Collectors.toList());

            dataModel.put("favourites", favouritesId);
        }

        String servletPath = req.getServletPath();

        dataModel.put("servletPath", servletPath);
        dataModel.put("favourites", favouritesId);
        dataModel.put("maxPageSize", maxPage);
        dataModel.put("currentPage", currentPage);
        model.addAllAttributes(dataModel);

        return "favorites";
    }

}
