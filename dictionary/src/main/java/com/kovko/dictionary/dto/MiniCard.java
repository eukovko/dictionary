package com.kovko.dictionary.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class MiniCard {

    private Integer sourceLanguage;
    private Integer targetLanguage;
    private String heading;
    private Translation translation;
    private List<String> seeAlso;

}
