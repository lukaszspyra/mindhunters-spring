package com.spyrka.mindhunters.email;


import com.spyrka.mindhunters.models.dto.FullDrinkView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminEmailBuilder implements EmailBuildStrategy {


    @Override
    public String createContent(List<FullDrinkView> drinks) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      .colored {\n" +
                "        color: #ffc107;\n" +
                "      }\n" +
                "      #body {\n" +
                "        font-size: 14px;\n" +
                "      background-color: #3e1010;" +
                "        font-family: 'Cinzel', serif;" +
                "      color: #ffc107;}\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id='body'>\n" +
                "      <p>Dear Admin,</p>\n" +
                "      <p>Drink recipes awaiting your approval:</p>\n<ul class='colored'>");

        drinks.forEach(d -> stringBuilder.append("<li>").append(d.toString()).append("</li>"));

        stringBuilder.append("</ul>" +
                "      <p>Have fun,<br>" +
                "Mindhunters Virtual Bartender<br>http://mindhunters.jjdd9.is-academy.pl/</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>");

        return stringBuilder.toString();
    }
}
