package com.kovko.anki.json.config;

import com.kovko.anki.json.util.LeachAction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
public class LapsedCardsConfiguration {

    /**
     * Number of learning steps and delays between them
     */
    private final List<Integer> successiveDelays = new ArrayList<>(List.of(10));
    /**
     * Actions for a leech card
     */
    private LeachAction leechAction = LeachAction.SUSPEND;
    /**
     * Number of lapses before mark this card as a leech
     * Each consecutive interval when you will get leech warning
     * will be half of initial
     */
    private int leechFails = 8;
    /**
     * Minimal interval after card has become a leech
     * New interval doesn't depend on learning steps and intervals
     */
    private int minimalIntervalAfterLeech = 1;
    /**
     * Factor for the current interval
     * when the card is being lapsed
     */
    // TODO: 5/30/2020 This field isn't used
    private BigDecimal lapseFactor = BigDecimal.ZERO;

}
