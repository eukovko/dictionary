package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Deck;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class DeckSerializer extends StdSerializer<Deck> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "desc";
    private static final String MODIFICATION_TIME = "mod";
    private static final String UPDATE_SEQUENCE_NUMBER = "usn";
    private static final String EXTENDED_REVIEW = "extendRev";
    private static final String EXTENDED_NEW = "extendNew";
    private static final String COLLAPSED = "collapsed";
    private static final String BROWSER_COLLAPSED = "browserCollapsed";
    private static final String LEARNED_TODAY = "lrnToday";
    private static final String REVIEWED_TODAY = "revToday";
    private static final String NEW_TODAY = "newToday";
    private static final String TIME_TODAY = "timeToday";
    private static final String DECK_TYPE = "dyn";
    private static final String CONFIGURATION = "conf";

    public DeckSerializer() {
        super(Deck.class);
    }

    @Override
    public void serialize(Deck deck, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();

        gen.writeNumberField(ID, deck.getId());
        gen.writeStringField(NAME, deck.getName());
        gen.writeStringField(DESCRIPTION, deck.getDescription());
        gen.writeNumberField(MODIFICATION_TIME, deck.getModificationTime());
        gen.writeNumberField(UPDATE_SEQUENCE_NUMBER, deck.getUpdateSequenceNumber());
        gen.writeNumberField(EXTENDED_REVIEW, deck.getExtendedReview());
        gen.writeNumberField(EXTENDED_NEW, deck.getExtendedNew());
        gen.writeBooleanField(COLLAPSED, deck.isCollapsed());
        gen.writeBooleanField(BROWSER_COLLAPSED, deck.isBrowserCollapsed());

        gen.writeArrayFieldStart(LEARNED_TODAY);
        for (Integer i : deck.getLearnedToday()) {
            gen.writeNumber(i);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(REVIEWED_TODAY);
        for (Integer i : deck.getReviewedToday()) {
            gen.writeNumber(i);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(NEW_TODAY);
        for (Integer i : deck.getNewToday()) {
            gen.writeNumber(i);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(TIME_TODAY);
        for (Integer i : deck.getTimeToday()) {
            gen.writeNumber(i);
        }
        gen.writeEndArray();

        gen.writeNumberField(DECK_TYPE, deck.getDeckType().getTypeCode());
        gen.writeNumberField(CONFIGURATION, deck.getConfiguration().getId());

        gen.writeEndObject();
    }
}
