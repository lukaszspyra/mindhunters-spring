package com.spyrka.mindhunters.service;

import com.spyrka.mindhunters.context.ContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdultVerificationService {

    private void createAdultCookie(HttpServletResponse resp, String value) {
        Cookie cookie = new Cookie("age18", value);
        cookie.setMaxAge(60 * 60 * 24);
        resp.addCookie(cookie);
    }

    public void setAdultFromCookies(HttpServletRequest req, ContextHolder contextHolder) {
        Cookie[] c = req.getCookies();
        if (c != null) {
            final List<Cookie> age18s =
                    Arrays.stream(c).filter(e -> e.getName().equalsIgnoreCase("age18")).collect(Collectors.toList());
            if (!age18s.isEmpty()) {
                contextHolder.setADULT(age18s.get(0).getValue());
            }
        }
    }


    public void verifyAge18(HttpServletRequest req, HttpServletResponse resp, ContextHolder contextHolder) {
        String adult = req.getParameter("adult");
        String age18 = req.getParameter("age18");

        if (adult != null) {

            switch (adult) {
                case "true" -> {
                    contextHolder.setADULT(adult);
                    if (age18 != null) {

                        createAdultCookie(resp, "true");

                    }
                }
                case "false" -> {
                    contextHolder.setADULT(adult);
                    if (age18 != null) {

                        createAdultCookie(resp, "false");

                    }
                }
            }
        }
    }

}
