package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.CollectionConfiguration;
import com.kovko.anki.json.Deck;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class CollectionConfigurationSerializer extends StdSerializer<CollectionConfiguration> {

    private static final String CURRENT_DECK = "curDeck";
    private static final String ACTIVE_DECKS = "activeDecks";
    private static final String ADD_TO_CURRENT_DECK = "addToCur";
    private static final String CURRENT_MODEL = "curModel";
    private static final String REVIEW_ORDER = "newSpread";
    private static final String LEARN_AHEAD_LIMIT = "collapseTime";
    private static final String TIME_BOX_LIMIT = "timeLim";
    private static final String SHOW_ESTIMATE_TIMES = "estTimes";
    private static final String SHOW_REMAINING_CARDS = "dueCounts";
    private static final String HIGHEST_VALUE_FOR_NEW_DUE = "nextPos";
    private static final String BROWSER_SORT_TYPE = "sortType";
    private static final String REVERSE_SORTING = "sortBackwards";
    private static final String DAY_LEARN_FIRST = "dayLearnFirst";
    private static final String SCHEDULER_VERSION = "schedVer";
    private static final String BURY_NEW = "newBury";

    public CollectionConfigurationSerializer() {
        super(CollectionConfiguration.class);
    }

    @Override
    public void serialize(CollectionConfiguration configuration, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();

        gen.writeNumberField(CURRENT_DECK, configuration.getCurrentDeck().getId());
        gen.writeArrayFieldStart(ACTIVE_DECKS);
        for (Deck activeDeck : configuration.getActiveDecks()) {
            gen.writeNumber(activeDeck.getId());
        }
        gen.writeEndArray();
        gen.writeBooleanField(ADD_TO_CURRENT_DECK, configuration.isAddToCurrentDeck());
        gen.writeStringField(CURRENT_MODEL, String.valueOf(configuration.getCurrentModel().getId()));
        gen.writeNumberField(REVIEW_ORDER, configuration.getReviewOrder().getOrderCode());
        gen.writeNumberField(LEARN_AHEAD_LIMIT, configuration.getLearnAheadLimit());
        gen.writeNumberField(TIME_BOX_LIMIT, configuration.getTimeBoxLimit());
        gen.writeBooleanField(SHOW_ESTIMATE_TIMES, configuration.isShowEstimateTimes());
        gen.writeBooleanField(SHOW_REMAINING_CARDS, configuration.isShowRemainingCards());
        gen.writeNumberField(HIGHEST_VALUE_FOR_NEW_DUE, configuration.getHighestValueForNewDue());
        gen.writeStringField(BROWSER_SORT_TYPE, configuration.getBrowserSortType());
        gen.writeBooleanField(REVERSE_SORTING, configuration.isReverseSoring());
        gen.writeBooleanField(DAY_LEARN_FIRST, configuration.isDayLearnFirst());
        gen.writeNumberField(SCHEDULER_VERSION, configuration.getSchedulerVersion());
        gen.writeBooleanField(BURY_NEW, configuration.isBuryNew());

        gen.writeEndObject();
    }
}
