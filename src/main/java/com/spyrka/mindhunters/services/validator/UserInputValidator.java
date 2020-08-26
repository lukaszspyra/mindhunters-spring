package com.spyrka.mindhunters.services.validator;


import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserInputValidator {


    public boolean validateSpecialChars(String input) {
        Pattern compiledPattern = Pattern.compile("[\\p{L}0-9_. -]{2,30}");
        Matcher matcher = compiledPattern.matcher(input);
        return matcher.matches();
    }

    public String removeExtraSpaces(String input) {
        return input.replaceAll(" +", " ");
    }

    public boolean validatePageNumber(String page) {
        Pattern compiledPattern = Pattern.compile("[1-9]+[0-9]*");
        Matcher matcher = compiledPattern.matcher(page);
        return matcher.matches();
    }

    public Long stringToLongConverter(String number) {
        number = number.replaceAll(",", "");
        number = number.replaceAll("\\.", "");
        if (NumberUtils.isCreatable(number)) {
            return Long.parseLong(number);
        }
        return -1L;
    }

    public int compareCurrentPageWithMaxPage(int currentPage, int maxPage) {
        if (currentPage < 0 || currentPage > maxPage) {
            currentPage = 1;
        }
        return currentPage;
    }

    public int getFirstPageWhenWrongPageInput(String pageNumberReq) {

        if (pageNumberReq == null || pageNumberReq.trim().isEmpty() || !validatePageNumber(pageNumberReq)) {
            return 1;
        }

        return Integer.parseInt(pageNumberReq);
    }

}
