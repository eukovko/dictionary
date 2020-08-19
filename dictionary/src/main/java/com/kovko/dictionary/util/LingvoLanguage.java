package com.kovko.dictionary.util;

import lombok.AllArgsConstructor;

/**
 * Author: eukovko
 * Date: 6/20/2020
 */
@AllArgsConstructor
public enum LingvoLanguage {

    EN(1033),
    RU(1049),
    IT(1040);

    private final int lingvoCode;

    public int getLingvoCode() {
        return lingvoCode;
    }
}
