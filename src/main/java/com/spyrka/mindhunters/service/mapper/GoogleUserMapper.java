package com.spyrka.mindhunters.service.mapper;


import com.spyrka.mindhunters.model.dto.UserGoogleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GoogleUserMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleUserMapper.class.getName());

    public UserGoogleView mapGoogleResponseToUserGoogleView(Map<String, Object> googleResponse) {
        UserGoogleView userView = new UserGoogleView();
        userView.setName(googleResponse.getOrDefault("name", "Unanimous name").toString());
        userView.setEmail(googleResponse.getOrDefault("email", "Unanimous email").toString());

        LOGGER.info("UserView set from google Oauth2 response");
        return userView;
    }

}
