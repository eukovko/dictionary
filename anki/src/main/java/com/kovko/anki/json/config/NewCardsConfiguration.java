package com.kovko.anki.json.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
public class NewCardsConfiguration {

    /**
     * Defines whether sibling cards may be shown together
     */
    private boolean bury = false;
    /**
     * Number of learning steps and delays between them
     */
    private final List<Integer> successiveDelays = new ArrayList<>(List.of(1,10));
    /**
     * The first number corresponds to Graduating interval
     * (the delay between answering Good on a card with no steps left, and seeing the card again)
     * The second number corresponds to Easy interval
     * (the delay between answering easy on a learning card and seeing it in review mode for the first time)
     * The third number presumably the interval after answering Easy
     * to the cards which are already had an Easy interval
     */
    private final List<Integer> learningModeDelays = new ArrayList<>(List.of(1,4,7));
    /**
     * Sets the show order of new cards
     * 0 - show new cards in random order
     * 1 - show cards in the order of their due
     */
    private int cardOrder = 1;
    /**
     * The new ease factor of the card in permille (parts per thousand).
     * If the ease factor is 2500, the card’s interval will be
     * multiplied by 2.5 the next time you press Good.
     */
    private int initialEaseFactor = 2500;
    /**
     * Maximum number of new cards shown per day
     */
    private int cardsPerDay = 50;
    /**
     * This property seems to be unused
     */
    private boolean separate = true;

}
