package com.kovko.dictionary.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Data
public class Article {

    private Long id;
    private String title;
    private String dictionary;
    private String soundName;
    private String articleId;
    private List<Meaning> meanings = new ArrayList<>();
}
