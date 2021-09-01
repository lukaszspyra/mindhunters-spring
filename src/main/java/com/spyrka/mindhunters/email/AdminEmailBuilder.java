package com.spyrka.mindhunters.email;


import com.spyrka.mindhunters.model.dto.FullDrinkView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminEmailBuilder implements EmailBuildStrategy {


    @Override
    public String createContent(List<FullDrinkView> drinks) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("""
                <html>
                                
                  <head>
                                
                    <style>
                                
                      .colored {
                                
                        color: #ffc107;
                                
                      }
                                
                      #body {
                                
                        font-size: 14px;
                                
                      background-color: #3e1010;
                        font-family: 'Cinzel', serif;
                      color: #ffc107;}
                                
                    </style>
                                
                  </head>
                                
                  <body>
                                
                    <div id='body'>
                                
                      <p>Dear Admin,</p>
                                
                      <p>Drink recipes awaiting your approval:</p>
                <ul class='colored'>
                """);

        drinks.forEach(d -> stringBuilder.append("<li>").append(d.toString()).append("</li>"));

        stringBuilder.append("""
                </ul>
                      <p>Have fun,<br>
                Mindhunters Virtual Bartender<br>http://mindhunters.jjdd9.is-academy.pl/</p>
                    </div>
                                
                  </body>
                                
                </html>
                """);

        return stringBuilder.toString();
    }
}
