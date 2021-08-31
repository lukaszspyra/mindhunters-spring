package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.email.EmailSender;
import com.spyrka.mindhunters.email.UserDrinkProposalEmailBuilder;
import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.service.AdminManagementRecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AdminDrinkEditController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDrinkEditController.class.getName());

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @Autowired
    private UserDrinkProposalEmailBuilder userDrinkProposalEmailBuilder;

    @Autowired
    private EmailSender emailSender;

    /**
     * Accepts or rejects drink removal proposition
     *
     * @param model
     * @param req
     * @param resp
     * @throws IOException
     */
    @PostMapping("/admin/to-approve-list/edit")
    protected void doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idToApprove = req.getParameter("approve");
        String idToReject = req.getParameter("reject");

        if (idToApprove != null && !idToApprove.isBlank()) {
            Drink approvedDrink = adminManagementRecipeService.approveDrinkUpdate(Long.parseLong(idToApprove));
            String emailContent = userDrinkProposalEmailBuilder.createContent(approvedDrink, "accepted");
            emailSender.sendEmail(emailContent, approvedDrink.getConfirmUserEmail());
        }

        if (idToReject != null && !idToReject.isBlank()) {
            Drink deletedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToReject));
            String userEmail = deletedDrink.getConfirmUserEmail();
            String emailContent = userDrinkProposalEmailBuilder.createContent(deletedDrink, "deleted");
            emailSender.sendEmail(emailContent, userEmail);

        }

        resp.sendRedirect("/admin/to-approve-list");
    }
}
