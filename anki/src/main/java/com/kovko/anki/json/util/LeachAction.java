package com.kovko.anki.json.util;

import lombok.Getter;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
@Getter
public enum LeachAction {

    SUSPEND(0), MARK(1);

    private int leechActionCode;

    LeachAction(int leechActionCode) {
        this.leechActionCode = leechActionCode;
    }
}
