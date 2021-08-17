package com.spyrka.mindhunters.controllers.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    /**
     * Check if REST API is responding, giving HTTP status code OK.
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void checkHealth() {
        //empty
    }
}