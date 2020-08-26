package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.services.DrinkService;
import com.spyrka.mindhunters.services.RatingService;
import com.spyrka.mindhunters.services.StatisticsService;
import com.spyrka.mindhunters.services.UserService;
import com.spyrka.mindhunters.services.validator.UserInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SingleViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleViewController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private UserService userService;


    @GetMapping("/single-view")
    protected String doGet(Model dataModel, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        final String idParam = req.getParameter("drink");
        Long drinkId = userInputValidator.stringToLongConverter(idParam);


        String email = getCredentials(req, dataModel.asMap());

        if (drinkId < 0) {
            dataModel.addAttribute("errorMessage", "Wrong input.\n");
        } else {
            final FullDrinkView foundDrinkById = passDTOtoView(drinkId, dataModel.asMap());

            statisticsService.addToStatistics(foundDrinkById);

            dataModel.addAttribute("rating", ratingService.getCalculatedRatingByDrinkId(drinkId));
        }

        sentFavouritesToView(dataModel.asMap(), email);

        return "singleDrinkView";
    }

    private void sentFavouritesToView(Map<String, Object> dataModel, String email) {
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
    }

    private FullDrinkView passDTOtoView(Long drinkId, Map<String, Object> dataModel) {
        final FullDrinkView foundDrinkById = drinkService.getFullDrinkViewById(drinkId);


        if (foundDrinkById == null) {
            dataModel.put("errorMessage", "Drink not found.\n");
        }

        dataModel.put("drink", foundDrinkById);
        return foundDrinkById;
    }

    private String getCredentials(HttpServletRequest req, Map<String, Object> dataModel) {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        return contextHolder.getEmail();
    }


}
