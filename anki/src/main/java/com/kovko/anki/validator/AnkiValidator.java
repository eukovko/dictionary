package com.kovko.anki.validator;

import com.kovko.anki.json.util.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 6/7/2020
 */
public class AnkiValidator {

    public List<String> validateFont(Font font){

        ArrayList<String> validationResult = new ArrayList<>();

        if (font.getName() ==  null) {
            validationResult.add("Font name is null");
        }
        if (font.getName().isBlank()){
            validationResult.add("Font name is blank");
        }
        if (font.getSize() == null) {
            validationResult.add("Font size is null");
        }
        if (font.getSize()<1) {
            validationResult.add("Font size must be a positive number");
        }

        return validationResult;
    }
}
