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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminDrinkRemoveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDrinkRemoveController.class.getName());
    private static final String REJECTED = "rejected";

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @Autowired
    private UserDrinkProposalEmailBuilder userDrinkProposalEmailBuilder;

    @Autowired
    private EmailSender emailSender;

    /**
     * Sends drinks proposal for removal to admin view
     *
     * @param model contains all data required for the template
     * @param req
     * @param resp
     * @return Template with given name
     * @throws UnsupportedEncodingException
     */
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
                dataModel.put("drinkList", toApproveList);
            }
        }

        dataModel.put("typeOfAction", REJECTED);
        dataModel.put("url", "delete");
        model.addAllAttributes(dataModel);
        return "recipeToApproveList";
    }


    /**
     * Accepts or Rejects drinks proposal sent by the user to database responsible admin
     *
     * @param model carries data for templates
     * @param req with drink id and approved/reject action
     * @param resp
     * @return View with any remaining recipes awaiting acceptance for removal
     * @throws IOException
     */
    @PostMapping("/admin/to-approve-list/delete")
    protected String doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        String idToApprove = req.getParameter("approve");
        String idToReject = req.getParameter("reject");

        if (idToApprove != null && !idToApprove.isBlank()) {
            Drink approvedDrink = adminManagementRecipeService.setApprovedDeleteDrink(Long.parseLong(idToApprove));
            String emailContent = userDrinkProposalEmailBuilder.createContent(approvedDrink, "accepted");
            emailSender.sendEmail(emailContent, approvedDrink.getConfirmUserEmail());
        }

        if (idToReject != null && !idToReject.isBlank()) {
            Drink rejectedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToReject));
            String userEmail = rejectedDrink.getConfirmUserEmail();
            String emailContent = userDrinkProposalEmailBuilder.createContent(rejectedDrink, REJECTED);
            emailSender.sendEmail(emailContent, userEmail);
        }

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {
            List<FullDrinkView> toApproveList = drinkService.findDeletedDrinksToApprove();
            if (!toApproveList.isEmpty()) {
                dataModel.put("drinkList", toApproveList);
            }

        }
        dataModel.put("typeOfAction", REJECTED);
        dataModel.put("url", "/delete");

        model.addAllAttributes(dataModel);
        return "recipeToApproveList";
    }

}
