package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.services.imageFileUpload.ImageUploadProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Serves pictures from the server
 */
@Controller
@RequestMapping("/pictures/*")
public class ImagesServingController {

    @Autowired
    ImageUploadProcessor fileUploadProcessor;

    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();
        String fileName = requestURI.substring(10);
        File file = new File(fileUploadProcessor.getUploadImageFilesPath(), fileName);
        resp.setHeader("Content-Type", Files.probeContentType(file.toPath()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
