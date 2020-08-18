package com.kovko.anki.json;

import com.kovko.anki.json.util.ReviewOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Setter
@Getter
public class CollectionConfiguration {

    /**
     * Last active deck
     */
    private Deck currentDeck;
    /**
     * Array of the last active deck and it's descendants (subdecks)
     */
    private List<Deck> activeDecks;
    /**
     * Defines that when adding a card it will go to a current deck
     * Otherwise another the deck will be chosen based on the note type
     */
    private boolean addToCurrentDeck = true;
    /**
     * The last model was used
     */
    private Model currentModel;
    /**
     * Review order
     */
    private ReviewOrder reviewOrder = ReviewOrder.MIX;
    /**
     * Learn ahead limit in seconds
     */
    private int learnAheadLimit = 1200;
    /**
     * Create a period from minutes and Anki will tell
     * how many cards you've learned after this period elapsed
     */
    private int timeBoxLimit = 0;
    /**
     * Defines if an estimated time will be shown
     */
    private boolean showEstimateTimes = true;
    /**
     * Defines if to show remaining cards count during review
     */
    private boolean showRemainingCards = true;
    /**
     * The highest value of a due value of a new card
     */
    private int highestValueForNewDue = 1;
    /**
     * String value which defines sort time in browser
     */
    // TODO: 5/20/2020 Make an ENUM (aqt.browsers.DataModel.activeCols)
    private String browserSortType = "noteFld";
    /**
     * Defines if the sorting will be reversed
     */
    private boolean reverseSoring = false;
    /**
     * Defines if to show learning cards with with larger steps before review
     */
    private boolean dayLearnFirst = false;
    /**
     * This field is not used and defaults to true
     */
    private boolean buryNew = true;
    /**
     * Scheduler version
     */
    private int schedulerVersion = 1;

}
