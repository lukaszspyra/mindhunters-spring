package com.spyrka.mindhunters.oauth;

import com.spyrka.mindhunters.context.ContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GoogleLogoutController {

    @GetMapping("/oauth_logout")
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        contextHolder.invalidate();
        resp.sendRedirect("/");
    }
}
