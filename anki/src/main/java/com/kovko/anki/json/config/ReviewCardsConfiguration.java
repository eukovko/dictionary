package com.kovko.anki.json.config;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Setter
@Getter
public class ReviewCardsConfiguration {

    /**
     * Defines whether sibling cards may be shown together
     */
    private boolean bury = false;
    /**
     * Easy bonus which is used as factor for Good interval
     */
    private BigDecimal easinessIncrement = BigDecimal.valueOf(1.30);
    /**
     * Adds "fuzzines" to the interval
     * in order to prevent cards stick together
     * Default is 5% of variation
     */
    private BigDecimal randomIntervalFactor = BigDecimal.valueOf(0.05);
    /**
     * Factor for Anki geneated intervals
     */
    private BigDecimal intervalFactor = BigDecimal.ONE;
    /**
     * Maximum interval for a card in days
     */
    private BigDecimal maxReviewInterval = BigDecimal.valueOf(36500);
    /**
     * This field currently is unused
     */
    private int minSpace = 1;
    /**
     * Factor for the current interval for a hard card
     */
    private BigDecimal hardFactor = BigDecimal.valueOf(1.20);
    /**
     * Maximum number of cards shown per day
     */
    private int cardsPerDay = 200;

}
