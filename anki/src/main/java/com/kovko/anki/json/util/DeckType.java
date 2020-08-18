package com.kovko.anki.json.util;

import lombok.Getter;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
@Getter
public enum DeckType {

    REGULAR(0), FILTERED(1);

    private int typeCode;

    DeckType(int typeCode) {
        this.typeCode = typeCode;
    }
}
