package com.kovko.anki;

import com.kovko.anki.json.CollectionConfiguration;
import com.kovko.anki.json.Deck.Decks;
import com.kovko.anki.json.DeckConfiguration.DeckConfigurations;
import com.kovko.anki.json.Model.Models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Collection {

    /**
     * Collection id
     * Arbitrary number as in the database
     * this table contains only one row
     */
    private long id = 1;
    /**
     * Creation time in epoch seconds
     */
    // TODO: 5/21/2020 timestamp of the creation date. It's correct up to the day.
    //  For V1 scheduler, the hour corresponds to starting a new day. By default, new day is 4
    private long creationDate = Instant.now().getEpochSecond();
    /**
     * The modification time in epoch milliseconds
     */
    private long lastModified = Instant.now().toEpochMilli();
    /**
     * The modification time of a schema
     * If server and client modification time are different
     * full-synс is required
     */
    private long schemaLastModified = 0;
    /**
     * The version of the collection
     */
    // TODO: 5/21/2020 Check documentation if it should be different for each configuration
    private long version = 11;
    /**
     * Field is unused
     */
    private int dirty = 0;
    /**
     * Defines if the configuration has changes and need to be updated
     */
    private int updateSequenceNumber = 0;
    /**
     * Last synchronization time in epoch milliseconds
     */
    private long lastSyncTime = 0;
    /**
     * Cached tags used in collection
     * Tag against the number (of cards?)
     */
    private Map<String, Integer> tags = new HashMap<>();
    /**
     * Collection configuration
     */
    private CollectionConfiguration configuration;
    /**
     * Models of the collection
     */
    private Models models;
    /**
     * Decks of the collection
     */
    private Decks decks;
    /**
     * Decks configuration of the collection
     */
    private DeckConfigurations deckConfiguration;

}
