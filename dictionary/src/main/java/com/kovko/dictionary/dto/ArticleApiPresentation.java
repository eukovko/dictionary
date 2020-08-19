package com.kovko.dictionary.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 6/28/2020
 */
@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class ArticleApiPresentation {

    private String title;
    private String dictionary;
    private String articleId;
    private List<ArticleNode> titleMarkup;
    private List<ArticleNode> body;
}
