package com.spyrka.mindhunters.jsonsupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spyrka.mindhunters.models.json.DrinkJson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JsonParserApiBean {

    public List<DrinkJson> jsonDrinkReaderFromString(String json) throws IOException {
        ObjectMapper mapperApi = new ObjectMapper();
        mapperApi.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JsonNode jsonNode = mapperApi.readTree(json);

        List<DrinkJson> drink = (List<DrinkJson>) mapperApi.readValue(jsonNode.get("drinks").toString(),
                new TypeReference<List<DrinkJson>>() {
                });

        return drink;
    }

}
