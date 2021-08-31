package com.spyrka.mindhunters.jsonsupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spyrka.mindhunters.model.json.CategoryJson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JsonCategoryApiReader {

    private JsonCategoryApiReader() {
    }

    public static List<CategoryJson> jsonCategoryReader(String jsonCat) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JsonNode jsonNode = mapper.readTree(jsonCat);

        List<CategoryJson> categoryJsonList = (List<CategoryJson>) mapper.readValue(jsonNode.get("drinks").toString(),
                new TypeReference<List<CategoryJson>>() {
                });
        return categoryJsonList;
    }
}
