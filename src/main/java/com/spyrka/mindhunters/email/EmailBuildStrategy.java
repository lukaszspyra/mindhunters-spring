package com.spyrka.mindhunters.email;


import com.spyrka.mindhunters.model.dto.FullDrinkView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmailBuildStrategy {

    String createContent(List<FullDrinkView> drinks);

}
