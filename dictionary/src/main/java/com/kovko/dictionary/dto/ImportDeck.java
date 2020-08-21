package com.kovko.dictionary.dto;

import lombok.Data;

/**
 * Author: eukovko
 * Date: 7/6/2020
 */
@Data
public class ImportDeck {

    private String deckName;
    private String packageName;
    private String dictionaryName;
    private MinicardTranslationBatch minicardTranslationBatch;
}
