package com.spyrka.mindhunters.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GoogleLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleLoginController.class.getName());

    @GetMapping("/oauth_login")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("Redirecting to Oauth2 Google");
        resp.sendRedirect("/oauth2callback");
    }

}
