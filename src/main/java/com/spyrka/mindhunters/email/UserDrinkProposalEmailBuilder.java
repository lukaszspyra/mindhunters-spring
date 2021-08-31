package com.spyrka.mindhunters.email;


import com.spyrka.mindhunters.model.Drink;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class UserDrinkProposalEmailBuilder{



    public String createContent(Drink drink, String msg) {


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
                "      <p>Dear User of Mindhunters Virtual Bartender" + ",</p>\n" +
                "      <p>Following proposals were " +  msg + " by our admins:</p><ul class='colored'>Recipe: ");

        stringBuilder.append(drink.getDrinkName().toUpperCase()).append(", created: ").append(drink.getDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm")));

        stringBuilder.append("</ul>" +
                "      <p>All the beast,<br>" +
                "Mindhunters Virtual Bartender</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>");

        return stringBuilder.toString();
    }
}
