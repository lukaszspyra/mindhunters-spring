package com.spyrka.mindhunters.context;

import com.spyrka.mindhunters.models.dto.UserView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ContextHolder {
    private static final String NAME = "name";
    private static final String ROLE = "role";
    private static final String EMAIL = "email";
    private static final String ID = "id";

    private static String ADULT = "adult";

    private final HttpSession httpSession;


    public ContextHolder(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public ContextHolder(HttpSession httpSession, UserView userView) {
        this.httpSession = httpSession;
        setContext(userView);
    }

    public String getADULT() {
        return (String) httpSession.getAttribute(ADULT);
    }

    public void setADULT(String ADULT) {
        httpSession.setAttribute(ContextHolder.ADULT, ADULT);;
    }

    public String getName() {
        return (String) httpSession.getAttribute(NAME);
    }

    public String getEmail() {
        return (String) httpSession.getAttribute(EMAIL);
    }

    public String getRole() {
        return Optional.ofNullable((String) httpSession.getAttribute(ROLE)).orElse("GUEST");
    }

    private void setContext(UserView userView) {

        httpSession.setMaxInactiveInterval(1800);

        httpSession.setAttribute(NAME, userView.getName());
        httpSession.setAttribute(EMAIL, userView.getEmail());
        httpSession.setAttribute(ID, userView.getId());
        httpSession.setAttribute(ROLE, userView.getRole());
        httpSession.removeAttribute(ADULT);
    }

    public void invalidate() {
        httpSession.removeAttribute(EMAIL);
        httpSession.removeAttribute(NAME);
        httpSession.removeAttribute(ROLE);
        httpSession.removeAttribute(ID);
        httpSession.removeAttribute(ADULT);
    }
}