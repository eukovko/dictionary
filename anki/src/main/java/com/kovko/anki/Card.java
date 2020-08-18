package com.kovko.anki;

import com.kovko.anki.json.Deck;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class Card {

    /**
     * Creation time in epoch milliseconds
     */
    private Long id = Instant.now().toEpochMilli();
    /**
     * Corresponding note id
     */
    private Note note;
    /**
     * Corresponding deck id
     */
    private Deck deck;
    /**
     * Card ordinal
     */
    private Integer ordinal;
    /**
     * Modification time in epoch seconds
     */
    private Long modificationTime = Instant.now().getEpochSecond();
    /**
     * Defines if the note has changes and need to be updated
     */
    private Integer updateSequenceNumber = -1;
    /**
     * Card type
     */
    private CardType type = CardType.NEW;
    /**
     * Queuing strategy
     */
    // TODO: 5/31/2020 Check with different versions of scheduler
    private Long queue = 0L;
    /**
     * Due parameter, depends on the card's type
     * Note id or random int for a new card
     * The number of days relative
     * to the collection creation for a due card
     * Timestamp for a learning card
     */
    private Long due;
    /**
     * Interval for SRS (spaced repetition) algorithm
     * Negative means seconds
     * Positive means days
     */
    private Long interval = 0L;
    /**
     * The ease factor in parts per thousand
     * If the factor is 2500 then after answering GOOD
     * interval will be multiplied by 2.5
     */
    private Long easeFactor = 0L;
    /**
     * The number of reviews
     */
    private Long numberOfReviews = 0L;
    /**
     * The number of lapses
     */
    private Long numberOfLapses = 0L;
    /**
     * The number which calculated with formula a*1000 + b
     * Where 'a' is the number of repetitions left today
     * and 'b' is the number of repetitions left to graduation
     */
    private Long left = 0L;
    /**
     * Works only for filtered deck
     * Contains the original due of the card
     */
    private Long originalDue = 0L;
    /**
     * Works only for filtered deck
     * Contains the original deck id
     */
    private Long originalDeckId = 0L;
    /**
     * Modulo 8 which represents a certain flag in browser
     * Red 1, Orange 2, Green 3, Blue 4, No flag 0
     */
    private Long flags = 0L;
    /**
     * Currently unused
     */
    private String data = "";

    public Card(Note note, int ordinal, Deck deck) {

        this.note = note;
        this.due = note.getId();
        this.deck = deck;
        this.ordinal = ordinal;
    }

    public Card(Note note, int ordinal) {

        this.note = note;
        this.due = note.getId();
        this.deck = note.getModel().getDefaultDeck();
        this.ordinal = ordinal;
    }

}
