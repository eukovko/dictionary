package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.DeckConfiguration;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class DeckConfigurationSerializer extends StdSerializer<DeckConfiguration> {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String MODIFICATION_TIME = "mod";
    private static final String UPDATE_SEQUENCE_NUMBER = "usn";
    private static final String TIMER_TIMEOUT = "maxTaken";
    private static final String AUTOPLAY = "autoplay";
    private static final String REPLAY = "replayq";
    private static final String SHOW_TIMER = "timer";
    private static final String LAPSE_CONFIG = "lapse";
    private static final String REVIEW_CONFIG = "rev";
    private static final String NEW_CONFIG = "new";

    public DeckConfigurationSerializer() {
        super(DeckConfiguration.class);
    }

    @Override
    public void serialize(DeckConfiguration configuration, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField(NAME, configuration.getName());
        gen.writeBooleanField(REPLAY, configuration.isAudioAnswerReplay());
        gen.writeObjectField(LAPSE_CONFIG, configuration.getLapsedCardsConfiguration());
        gen.writeObjectField(REVIEW_CONFIG, configuration.getReviewCardsConfiguration());
        gen.writeNumberField(TIMER_TIMEOUT, configuration.getTimerTimeout());
        gen.writeNumberField(ID, configuration.getId());
        gen.writeNumberField(MODIFICATION_TIME, configuration.getModificationTime());
        gen.writeNumberField(UPDATE_SEQUENCE_NUMBER, configuration.getUpdateSequenceNumber());
        gen.writeBooleanField(AUTOPLAY, configuration.isAudioAutoplay());
        gen.writeNumberField(SHOW_TIMER, configuration.isShowTimer()?1:0);
        gen.writeObjectField(NEW_CONFIG, configuration.getNewCardsConfiguration());
        gen.writeEndObject();
    }
}
