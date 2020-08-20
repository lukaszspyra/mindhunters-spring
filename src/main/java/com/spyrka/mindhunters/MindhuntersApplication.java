package com.spyrka.mindhunters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MindhuntersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindhuntersApplication.class, args);
    }

}
