package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.config.NewCardsConfiguration;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class NewCardsConfigurationSerializer extends StdSerializer<NewCardsConfiguration> {

    private static final String BURY = "bury";
    private static final String SUCCESSIVE_DELAYS = "delays";
    private static final String LEARNING_MODE_DELAYS = "ints";
    private static final String ORDER = "order";
    private static final String INITIAL_EASE_FACTOR = "initialFactor";
    private static final String PER_DAY = "perDay";
    private static final String SEPARATE = "separate";

    public NewCardsConfigurationSerializer() {
        super(NewCardsConfiguration.class);
    }

    @Override
    public void serialize(NewCardsConfiguration configuration, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeBooleanField(BURY, configuration.isBury());
        gen.writeArrayFieldStart(SUCCESSIVE_DELAYS);
        for (Integer successiveDelay : configuration.getSuccessiveDelays()) {
            gen.writeNumber(successiveDelay);
        }
        gen.writeEndArray();
        gen.writeArrayFieldStart(LEARNING_MODE_DELAYS);
        for (Integer learningDelay : configuration.getLearningModeDelays()) {
            gen.writeNumber(learningDelay);
        }
        gen.writeEndArray();
        gen.writeNumberField(ORDER, configuration.getCardOrder());
        gen.writeNumberField(INITIAL_EASE_FACTOR, configuration.getInitialEaseFactor());
        gen.writeNumberField(PER_DAY, configuration.getCardsPerDay());
        gen.writeBooleanField(SEPARATE, configuration.isSeparate());
        gen.writeEndObject();
    }
}
