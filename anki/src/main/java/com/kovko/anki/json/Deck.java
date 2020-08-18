package com.kovko.anki.json;

import com.kovko.anki.json.util.DeckType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
// TODO: 5/17/2020 Move default parameters to init method
// TODO: 5/23/2020 Add link to collection to remove *Today fields
public class Deck {

    /**
     * The time of creation in epoch milliseconds
     */
    private long id = Instant.now().toEpochMilli();
    /**
     * The name of the deck
     */
    private String name;
    /**
     * The time of modification in epoch seconds
     */
    private long modificationTime = Instant.now().getEpochSecond();
    /**
     * Defines if the deck has changes and need to be updated
     */
    private long updateSequenceNumber = 0;

    /**
     * The number of learned cards which will be added
     * to a custom study filtered deck to preview ahead
     */
    private int extendedReview = 50;
    /**
     * The number of new cards which will be added
     * to a custom study filtered deck to review ahead
     */
    private int extendedNew = 10;


    /**
     * Defines if the deck will be collapsed in main deck menu
     */
    private boolean collapsed = false;
    /**
     * Defines if the deck will be collapsed in browser view
     */
    // TODO: 5/30/2020 This field isn't used or default to false when absent
    private boolean browserCollapsed = false;


    // TODO: 5/19/2020 The first number is always the same for a deck, we can extract it
    // TODO: 5/19/2020 The second field may be filled only for real cards, and default to zero for import decks
    /**
     * The array of two numbers
     * First is the number of difference in days between
     * the creation of the collection and last update of this deck
     * Second is the number of cards learned today
     */
    private List<Integer> learnedToday = List.of(0,0);
    /**
     * The array of two numbers
     * First is the number of difference in days between
     * the creation of the collection and last update of this deck
     * Second is the number of cards reviewed today
     */
    private List<Integer> reviewedToday = List.of(0,0);
    /**
     * The array of two numbers
     * First is the number of difference in days between
     * the creation of the collection and last update of this deck
     * Second is the number of new cards added today
     */
    private List<Integer> newToday = List.of(0,0);


    /**
     * Currently unused field for custom study
     */
    private List<Integer> timeToday = List.of(0,0);

    // TODO: 5/19/2020 Change to booleand and translate while marshalling to JSON
    /**
     * Defines if this deck is filtered
     */
    private DeckType deckType = DeckType.REGULAR;

    // TODO: 5/19/2020 Add the class itself and marshall only id
    /**
     * Deck configuration (option group) id
     * Relate to dconf column
     */
    // TODO: 5/21/2020 Replace with DeckConfiguration instance and use wrapper for this field
    private DeckConfiguration configuration = new DeckConfiguration("DefaultDeckConfig");

    /**
     * Deck description
     */
    private String description = "";


    public Deck(String name) {
        this.name = name;
    }

    public Deck(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Author: eukovko
     * Date: 5/31/2020
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Decks {

        private List<Deck> decks;
    }
}
