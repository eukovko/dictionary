package com.kovko.anki;

import lombok.Getter;

/**
 * Author: eukovko
 * Date: 5/31/2020
 */
@Getter
public enum CardType {

    NEW(0), LEARNING(1), DUE(2), RELEARNING(3);

    private int typeCode;

    CardType(int typeCode) {
        this.typeCode = typeCode;
    }
}
