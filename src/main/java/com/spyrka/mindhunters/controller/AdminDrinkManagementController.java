package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.email.EmailSender;
import com.spyrka.mindhunters.email.UserDrinkProposalEmailBuilder;
import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.service.AdminManagementRecipeService;
import com.spyrka.mindhunters.service.DrinkService;
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

/**
 * Handles drink recipes management approval process
 * <p>
 * There can be new recipes, updates or removal of existing recipes.
 */
@Controller
public class AdminDrinkManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDrinkManagementController.class.getName());
    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @Autowired
    private UserDrinkProposalEmailBuilder userDrinkProposalEmailBuilder;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private DrinkService drinkService;

    /**
     * Sends all proposals (delete, add, edit) to admin view
     *
     * @param model
     * @param req
     * @param resp
     * @return view from given name template
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/admin/to-approve-list")
    protected String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        ContextHolder contextHolder = new ContextHolder(req.getSession());

        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {

            List<FullDrinkView> toApproveList = drinkService.findDrinksToApprove();

            if (!toApproveList.isEmpty()) {
                dataModel.put("drinkList", toApproveList);
            }
        }
        dataModel.put("url", "");
        model.addAllAttributes(dataModel);
        return "recipeToApproveList";
    }

        /**
     * Approves or rejects new drinks proposition, based on the request parameter
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @PostMapping("/admin/to-approve-list/add")
    public void newDrink(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idToCreate = req.getParameter("approve");
        String idToReject = req.getParameter("reject");

        if (idToCreate != null && !idToCreate.isBlank()) {
            Drink approvedDrink = adminManagementRecipeService.approveNewDrinkCreation(Long.parseLong(idToCreate));
            LOGGER.info("Drink proposition {}.", ACCEPTED);
            sendEmailToAuthor(approvedDrink, ACCEPTED);
        }

        if (idToReject != null && !idToReject.isBlank()) {
            Drink rejectedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToReject));
            LOGGER.info("Drink proposition {}.", REJECTED);
            sendEmailToAuthor(rejectedDrink, REJECTED);
        }
        resp.sendRedirect("/admin/to-approve-list");
    }


    /**
     * Accepts or rejects drink update proposition, based on the request parameter
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @PostMapping("/admin/to-approve-list/edit")
    public void updateDrink(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idToApprove = req.getParameter("approve");
        String idToReject = req.getParameter("reject");

        if (idToApprove != null && !idToApprove.isBlank()) {
            Drink approvedDrink = adminManagementRecipeService.approveDrinkUpdate(Long.parseLong(idToApprove));
            LOGGER.info("Drink proposition {}.", ACCEPTED);
            sendEmailToAuthor(approvedDrink, ACCEPTED);
        }

        if (idToReject != null && !idToReject.isBlank()) {
            Drink rejectedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToReject));
            LOGGER.info("Drink proposition {}.", REJECTED);
            sendEmailToAuthor(rejectedDrink, REJECTED);
        }
        resp.sendRedirect("/admin/to-approve-list");
    }


    /**
     * Accepts or rejects drink removal proposition, based on the request parameter
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @PostMapping("/admin/to-approve-list/delete")
    public void doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idToApprove = req.getParameter("approve");
        String idToReject = req.getParameter("reject");

        if (idToApprove != null && !idToApprove.isBlank()) {
            Drink approvedDrink = adminManagementRecipeService.setApprovedDeleteDrink(Long.parseLong(idToApprove));
            LOGGER.info("Drink proposition {}.", ACCEPTED);
            sendEmailToAuthor(approvedDrink, ACCEPTED);
        }

        if (idToReject != null && !idToReject.isBlank()) {
            Drink rejectedDrink = adminManagementRecipeService.rejectDrinkProposal(Long.parseLong(idToReject));
            LOGGER.info("Drink proposition {}.", REJECTED);
            sendEmailToAuthor(rejectedDrink, REJECTED);
        }

        resp.sendRedirect("/admin/to-approve-list");
    }

    private void sendEmailToAuthor(Drink drinkProposal, String action) {
        String userEmail = drinkProposal.getConfirmUserEmail();
        String emailContent = userDrinkProposalEmailBuilder.createContent(drinkProposal, action);
        emailSender.sendEmail(emailContent, userEmail);
        LOGGER.info("Email sent to recipe author - proposal {}.", action);
    }
}
