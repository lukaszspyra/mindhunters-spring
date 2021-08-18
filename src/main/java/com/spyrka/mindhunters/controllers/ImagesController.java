package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.services.imageFileUpload.ImageUploadProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Controller("/pictures/*")
public class ImagesController {

    @Autowired
    ImageUploadProcessor fileUploadProcessor;

    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String filename = URLDecoder.decode(req.getPathInfo().substring(1), StandardCharsets.UTF_8);
        File file = new File(fileUploadProcessor.getUploadImageFilesPath(), filename);
        resp.setHeader("Content-Type", Files.probeContentType(file.toPath()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
