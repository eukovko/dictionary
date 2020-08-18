package com.kovko.anki.json.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
@Data
@AllArgsConstructor
public class GenerationRequirements {

    private String generationType;
    private List<Integer> fields;

}
