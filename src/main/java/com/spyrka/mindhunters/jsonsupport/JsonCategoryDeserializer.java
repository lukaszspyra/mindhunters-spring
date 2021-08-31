package com.spyrka.mindhunters.jsonsupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.spyrka.mindhunters.model.json.CategoryJson;

import java.io.IOException;

public class JsonCategoryDeserializer extends JsonDeserializer<CategoryJson> {

    @Override
    public CategoryJson deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        CategoryJson categoryJson = new CategoryJson();

        JsonNode readValueAsTree = jsonParser.readValueAsTree();

        categoryJson.setCategoryName(readValueAsTree.get("strCategory").asText());

        return categoryJson;
    }
}