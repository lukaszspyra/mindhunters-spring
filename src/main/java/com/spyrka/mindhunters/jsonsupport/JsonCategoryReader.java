package com.spyrka.mindhunters.jsonsupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spyrka.mindhunters.models.json.CategoryJson;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonCategoryReader {

    private JsonCategoryReader() {
    }

    public static List<CategoryJson> jsonCategoryReader(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        File json = new File(fileName);
        JsonNode jsonNode = mapper.readTree(json);

        return (List<CategoryJson>) mapper.readValue(jsonNode.get("drinks").toString(),
                new TypeReference<List<CategoryJson>>() {
                });
    }
}
