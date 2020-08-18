package com.kovko.anki.json.util;

import lombok.Getter;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
@Getter
public enum ReviewOrder {

    MIX(0), REVIEW_FIRST(1), NEW_FIRST(2);

    private final int orderCode;

    ReviewOrder(int orderCode) {
        this.orderCode = orderCode;
    }
}
