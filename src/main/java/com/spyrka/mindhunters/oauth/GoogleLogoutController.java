package com.spyrka.mindhunters.oauth;

import com.spyrka.mindhunters.context.ContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GoogleLogoutController {

    @GetMapping("/logout")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        contextHolder.invalidate();
        resp.sendRedirect("/");
    }
}
