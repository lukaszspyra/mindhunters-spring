package com.spyrka.mindhunters.controllers;

import com.spyrka.mindhunters.exceptions.JsonNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Controller
public class FileUploadProcessor {

    private static final String SETTINGS_FILE = "settings.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadProcessor.class.getName());

    public File uploadJsonFile(Part filePart) throws IOException, JsonNotFound {

        String fileName = Paths.get(filePart.getSubmittedFileName())
                .getFileName().toString();

        if (fileName == null || fileName.isEmpty()) {
            throw new JsonNotFound("No JSON file has been uploaded");
        }

        File file = new File(getUploadJsonFilesPath() + fileName);
        Files.deleteIfExists(file.toPath());

        InputStream fileContent = filePart.getInputStream();

        Files.copy(fileContent, file.toPath());

        fileContent.close();

        return file;
    }

    public String getUploadJsonFilesPath() throws IOException {
        Properties settings = new Properties();
        settings.load(Objects.requireNonNull(Thread.currentThread()
                        .getContextClassLoader().getResource(SETTINGS_FILE))
                .openStream());
        LOGGER.debug("Loaded setting with upload path for images into Properties");
        return settings.getProperty("Upload.Path.IMAGE");
    }
}
