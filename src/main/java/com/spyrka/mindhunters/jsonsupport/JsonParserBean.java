package com.spyrka.mindhunters.jsonsupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spyrka.mindhunters.model.json.DrinkJson;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonParserBean {

    public List<DrinkJson> jsonDrinkReader(File json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JsonNode jsonNode = mapper.readTree(json);

        List<DrinkJson> drink = (List<DrinkJson>) mapper.readValue(jsonNode.get("drinks").toString(),
                new TypeReference<List<DrinkJson>>() {
                });

        return drink;
    }

}
