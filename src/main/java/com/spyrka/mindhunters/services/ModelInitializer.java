package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.context.ContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModelInitializer {


    public Map<String, Object> initModel(HttpServletRequest httpServletRequest) {
        final Map<String, Object> model = new HashMap<>();
        initUserContext(httpServletRequest.getSession(), model);
        return model;
    }

    private void initUserContext(HttpSession httpSession, Map<String, Object> model) {
        ContextHolder contextHolder = new ContextHolder(httpSession);
        model.put("email", contextHolder.getEmail());
        model.put("role", contextHolder.getRole());
    }
}
