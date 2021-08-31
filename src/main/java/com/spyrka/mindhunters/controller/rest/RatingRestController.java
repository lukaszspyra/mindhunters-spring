package com.spyrka.mindhunters.controller.rest;


import com.spyrka.mindhunters.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping("/api/rate")
public class RatingRestController {


    @Autowired
    RatingService ratingService;

    @PostMapping("/drinks/{drinkId}/rating/{rate}")
    public ResponseEntity<Object> rateDrink(@PathVariable("drinkId") long drinkId,
                                            @PathVariable("rate") double rate,
                                            HttpServletRequest req,
                                            HttpServletResponse res) {

        String ip = req.getRemoteAddr();
        if (isIpCookieEmpty(req)) {
            ratingService.updateRating(drinkId, rate);
            createCookie(res, ip);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private void createCookie(HttpServletResponse res, String ip) {
        Cookie cookie = new Cookie("ip", ip);
        res.addCookie(cookie);
    }

    private boolean isIpCookieEmpty(HttpServletRequest req) {
        return Arrays.stream(req.getCookies())
                .filter(c -> "ip".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny().isEmpty();
    }
}
