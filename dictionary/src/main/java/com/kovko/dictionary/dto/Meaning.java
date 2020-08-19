package com.kovko.dictionary.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Data
public class Meaning {

    private Long id;
    private String title;
    private List<Example> examples = new ArrayList<>();
    private List<CardReference> cardReferenceEntities = new ArrayList<>();
}
