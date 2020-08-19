package com.kovko.dictionary.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kovko.dictionary.util.LingvoLanguage;
import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 7/24/2020
 */
@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class TranslationBatch {

    private List<String> words;
    private LingvoLanguage sourceLanguage;
    private LingvoLanguage targetLanguage;

}
