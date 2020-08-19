package com.kovko.dictionary.dto;

import lombok.Data;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Data
public class Example {

    private Long id;
    private String example;
    private String translation;

    @Override
    public String toString(){
        return String.format("%s - %s", example, translation);
    }
}
