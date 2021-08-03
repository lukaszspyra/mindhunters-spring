package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.email.EmailSender;
import com.spyrka.mindhunters.email.UserDrinkProposalEmailBuilder;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.services.AdminManagementRecipeService;
import com.spyrka.mindhunters.services.DrinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminDrinkDeleteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDrinkDeleteController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @Autowired
    private UserDrinkProposalEmailBuilder userDrinkProposalEmailBuilder;

    @Autowired
    private EmailSender emailSender;


    @GetMapping("/admin/to-approve-list/delete")
    protected String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        ContextHolder contextHolder = new ContextHolder(req.getSession());

        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {

            List<FullDrinkView> toApproveList = drinkService.findDeletedDrinksToApprove();

            if (!toApproveList.isEmpty()) {
                List<Object> toApproveListModel = toApproveList.stream()
                        .map(FullDrinkView::getId)
                        .map(aLong -> Integer.parseInt(aLong.toString()))
                        .collect(Collectors.toList());

                dataModel.put("drinkList", toApproveList);
            }

        }

        dataModel.put("typeOfAction", "deleted");
        dataModel.put("url", "delete");

        model.addAllAttributes(dataModel);

        return "recipeToApproveList";
    }


    @PostMapping("/admin/to-approve-list/delete")
    protected String doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        ContextHolder contextHolder = new ContextHolder(req.getSession());

        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        String idToCreate = req.getParameter("create");
        String idToDelete = req.getParameter("delete");

        if (idToCreate != null && !idToCreate.isBlank()) {

            Drink approvedDrink = adminManagementRecipeService.setApprovedDeleteDrink(Long.parseLong(idToCreate));
            String emailContent = userDrinkProposalEmailBuilder.createContent(approvedDrink, "accepted");
            emailSender.sendEmail(emailContent, approvedDrink.getConfirmUserEmail());
        }

        if (idToDelete != null && !idToDelete.isBlank()) {
            Drink deletedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToDelete));
            String userEmail = deletedDrink.getConfirmUserEmail();
            String emailContent = userDrinkProposalEmailBuilder.createContent(deletedDrink, "deleted");
            emailSender.sendEmail(emailContent, userEmail);

        }

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {

            List<FullDrinkView> toApproveList = drinkService.findDeletedDrinksToApprove();

            if (!toApproveList.isEmpty()) {
                List<Object> toApproveListModel = toApproveList.stream()
                        .map(FullDrinkView::getId)
                        .map(aLong -> Integer.parseInt(aLong.toString()))
                        .collect(Collectors.toList());

                dataModel.put("drinkList", toApproveList);
            }

        }

        dataModel.put("typeOfAction", "deleted");
        dataModel.put("url", "/delete");

        model.mergeAttributes(dataModel);

        return "recipeToApproveList";
    }

}
