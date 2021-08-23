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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminDrinkCreateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDrinkCreateController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @Autowired
    private UserDrinkProposalEmailBuilder userDrinkProposalEmailBuilder;

    @Autowired
    private EmailSender emailSender;


    @GetMapping("/admin/to-approve-list/add")
    public String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {
            List<FullDrinkView> toApproveList = drinkService.findNewDrinksToApprove();
            if (!toApproveList.isEmpty()) {
                dataModel.put("drinkList", toApproveList);
            }
        }

        dataModel.put("typeOfAction", "created");
        dataModel.put("url", "add");

        model.addAllAttributes(dataModel);
        return "recipeToApproveList";
    }


    @PostMapping("/admin/to-approve-list/add")
    public String doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        String idToCreate = req.getParameter("approve");
        String idToReject = req.getParameter("reject");

        if (idToCreate != null && !idToCreate.isBlank()) {
            Drink approvedDrink = adminManagementRecipeService.approveNewDrinkCreation(Long.parseLong(idToCreate));
            String emailContent = userDrinkProposalEmailBuilder.createContent(approvedDrink, "accepted");
            emailSender.sendEmail(emailContent, approvedDrink.getConfirmUserEmail());
        }

        if (idToReject != null && !idToReject.isBlank()) {
            Drink deletedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToReject));
            String userEmail = deletedDrink.getConfirmUserEmail();
            String emailContent = userDrinkProposalEmailBuilder.createContent(deletedDrink, "rejected");
            emailSender.sendEmail(emailContent, userEmail);
        }

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {
            List<FullDrinkView> toApproveList = drinkService.findDrinksToApprove();
            if (!toApproveList.isEmpty()) {
                dataModel.put("drinkList", toApproveList);
            }
        }

        dataModel.put("typeOfAction", "created");
        dataModel.put("url", "/add");

        model.addAllAttributes(dataModel);
        return "recipeToApproveList";
    }

}
