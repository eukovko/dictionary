package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.config.LapsedCardsConfiguration;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class LapsedCardsConfigurationSerializer extends StdSerializer<LapsedCardsConfiguration> {

    private static final String SUCCESSIVE_DELAYS = "delays";
    private static final String LEECH_ACTION = "leechAction";
    private static final String LEECH_FAILS = "leechFails";
    private static final String MIN_INTERVAL_AFTER_LEECH = "minInt";
    private static final String LAPSE_FACTOR = "mult";

    public LapsedCardsConfigurationSerializer() {
        super(LapsedCardsConfiguration.class);
    }

    @Override
    public void serialize(LapsedCardsConfiguration configuration, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeArrayFieldStart(SUCCESSIVE_DELAYS);
        for (Integer successiveDelay : configuration.getSuccessiveDelays()) {
            gen.writeNumber(successiveDelay);
        }
        gen.writeEndArray();
        gen.writeNumberField(LEECH_ACTION, configuration.getLeechAction().getLeechActionCode());
        gen.writeNumberField(LEECH_FAILS, configuration.getLeechFails());
        gen.writeNumberField(MIN_INTERVAL_AFTER_LEECH, configuration.getMinimalIntervalAfterLeech());
        gen.writeNumberField(LAPSE_FACTOR, configuration.getLapseFactor());
        gen.writeEndObject();
    }
}
