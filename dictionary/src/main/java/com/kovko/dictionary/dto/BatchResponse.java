package com.kovko.dictionary.dto;

import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 7/25/2020
 */
@Data
public class BatchResponse {

    private List<String> unknownWords;
}
