package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.exceptions.JsonNotFound;
import com.spyrka.mindhunters.services.imageFileUpload.ImageUploadProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@Controller
public class AddingImagesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddingImagesController.class.getName());


    @Autowired
    ImageUploadProcessor imageUploadProcessor;

    @PostMapping(
            path = "/add-image"
    )
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part image = req.getPart("image");
        String imageUrl = "";
        try {
            imageUrl = "/pictures/" + imageUploadProcessor
                    .uploadImageFile(image).getName();
        } catch (JsonNotFound userImageNotFound) {
            LOGGER.warn(userImageNotFound.getMessage());
        }

    }


}
