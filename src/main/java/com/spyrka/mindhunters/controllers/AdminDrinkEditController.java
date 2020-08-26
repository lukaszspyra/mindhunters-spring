package com.spyrka.mindhunters.controllers;

import com.infoshareacademy.context.ContextHolder;
import com.infoshareacademy.domain.Drink;
import com.infoshareacademy.domain.dto.FullDrinkView;
import com.infoshareacademy.email.EmailSender;
import com.infoshareacademy.email.UserDrinkProposalEmailBuilder;
import com.infoshareacademy.freemarker.TemplateProvider;
import com.infoshareacademy.service.AdminManagementRecipeService;
import com.infoshareacademy.service.DrinkService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/admin/to-approve-list/edit")
public class AdminDrinkEditController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDrinkEditServlet.class.getName());

    @EJB
    private DrinkService drinkService;

    @EJB
    private AdminManagementRecipeService adminManagementRecipeService;

    @EJB
    private UserDrinkProposalEmailBuilder userDrinkProposalEmailBuilder;

    @EJB
    private EmailSender emailSender;

    @Inject
    private TemplateProvider templateProvider;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        ContextHolder contextHolder = new ContextHolder(req.getSession());

        String role = contextHolder.getRole();
        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        if (role != null && (role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMIN"))) {

            List<FullDrinkView> toApproveList = drinkService.findEditedDrinksToApprove();

            if (!toApproveList.isEmpty()) {
                List<Object> toApproveListModel = toApproveList.stream()
                        .map(FullDrinkView::getId)
                        .map(aLong -> Integer.parseInt(aLong.toString()))
                        .collect(Collectors.toList());

                dataModel.put("drinkList", toApproveList);
            }

        }

        dataModel.put("typeOfAction", "edited");
        dataModel.put("url", "/edit");

        Template template = templateProvider.getTemplate(getServletContext(), "receipeToApproveList.ftlh");

        try {
            template.process(dataModel, resp.getWriter());
        } catch (
                TemplateException e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            Drink approvedDrink = adminManagementRecipeService.setApprovedEditedDrink(Long.parseLong(idToCreate));
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

            List<FullDrinkView> toApproveList = drinkService.findEditedDrinksToApprove();

            if (!toApproveList.isEmpty()) {
                List<Object> toApproveListModel = toApproveList.stream()
                        .map(FullDrinkView::getId)
                        .map(aLong -> Integer.parseInt(aLong.toString()))
                        .collect(Collectors.toList());

                dataModel.put("drinkList", toApproveList);
            }

        }

        dataModel.put("typeOfAction", "edited");
        dataModel.put("url", "edit");

        Template template = templateProvider.getTemplate(getServletContext(), "receipeToApproveList.ftlh");

        try {
            template.process(dataModel, resp.getWriter());
        } catch (
                TemplateException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
