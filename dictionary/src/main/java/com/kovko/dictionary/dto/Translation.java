package com.kovko.dictionary.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Translation {

    private String heading;
    private String translation;
    private String dictionaryName;
    private String soundName;
    private Integer type;
    private String originalWord;
}
