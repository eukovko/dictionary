package com.kovko.dictionary.dto;

import lombok.Data;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Data
public class CardReference {

    private Long id;
    private String text;
    private String dictionary;
    private String articleId;
}
