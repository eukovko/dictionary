package com.kovko.anki.json.util;

import lombok.Getter;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
@Getter
public enum ModelType {

    STANDARD(0), CLOZE(1);

    private int modelCode;

    ModelType(int modelCode) {
        this.modelCode = modelCode;
    }

}
