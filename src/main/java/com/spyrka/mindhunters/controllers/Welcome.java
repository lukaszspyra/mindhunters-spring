package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.context.ContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class Welcome {

    @GetMapping(
            path = "/"
    )
    public String home(Model dataModel, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.addAttribute("name", contextHolder.getName());
        dataModel.addAttribute("role", contextHolder.getRole());

        return "welcomePage";

    }


}
