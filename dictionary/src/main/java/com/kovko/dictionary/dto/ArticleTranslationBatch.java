package com.kovko.dictionary.dto;

import com.kovko.dictionary.util.LingvoLanguage;
import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 8/21/2020
 */
@Data
public class ArticleTranslationBatch {

    private List<String> words;
    private LingvoLanguage sourceLanguage;
    private LingvoLanguage targetLanguage;
    private String dictionary;
}
