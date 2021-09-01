package com.spyrka.mindhunters.oauth;

import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.model.dto.UserGoogleView;
import com.spyrka.mindhunters.model.dto.UserView;
import com.spyrka.mindhunters.service.UserService;
import com.spyrka.mindhunters.service.mapper.GoogleUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleUserMapper googleUserMapper;

    @GetMapping("/oauth2callback")
    public void onSuccess(HttpServletRequest req, HttpServletResponse resp, OAuth2AuthenticationToken token) throws IOException {
        Map<String, Object> userDataFromGoogle = token.getPrincipal().getAttributes().entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("name") || entry.getKey().equalsIgnoreCase("email"))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        LOGGER.info("User {}, with email {}, logged in", userDataFromGoogle.get("name"), userDataFromGoogle.get("email"));

        UserGoogleView userGoogleView = googleUserMapper.mapGoogleResponseToUserGoogleView(userDataFromGoogle);
        UserView userView = userService.login(userGoogleView);
        new ContextHolder(req.getSession(), userView);

        LOGGER.info("User data from google saved to http session");
        resp.sendRedirect("/");
    }

}
