package com.spyrka.mindhunters.jsonsupport;

import com.spyrka.mindhunters.model.json.CategoryJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class JsonCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonCategoryService.class.getName());

    public void save(String pathToJsonFile) {
        List<CategoryJson> categoryFromJson = filerToListOfCategoryJson(pathToJsonFile);
    }

    private List<CategoryJson> filerToListOfCategoryJson(String pathToJsonFile) {
        try {
            return JsonCategoryReader.jsonCategoryReader(pathToJsonFile);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),"Json not created");
        }
        return List.of();
    }
}
