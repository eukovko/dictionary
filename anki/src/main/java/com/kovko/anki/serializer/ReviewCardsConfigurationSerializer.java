package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.config.ReviewCardsConfiguration;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class ReviewCardsConfigurationSerializer extends StdSerializer<ReviewCardsConfiguration> {

    private static final String BURY = "bury";
    private static final String EASYNESS_INCREMENT = "ease4";
    private static final String RANDOM_INTERVAL_FACTOR = "fuzz";
    private static final String INTERVAL_FACTOR = "ivlFct";
    private static final String MAX_REVIEW_INTERVAL = "maxIvl";
    private static final String MIN_SPACE = "minSpace";
    private static final String HARD_FACTOR = "hardFactor";
    private static final String PER_DAY = "perDay";

    public ReviewCardsConfigurationSerializer() {
        super(ReviewCardsConfiguration.class);
    }

    @Override
    public void serialize(ReviewCardsConfiguration configuration, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeBooleanField(BURY, configuration.isBury());
        gen.writeNumberField(EASYNESS_INCREMENT, configuration.getEasinessIncrement());
        gen.writeNumberField(RANDOM_INTERVAL_FACTOR, configuration.getRandomIntervalFactor());
        gen.writeNumberField(INTERVAL_FACTOR, configuration.getIntervalFactor());
        gen.writeNumberField(MAX_REVIEW_INTERVAL, configuration.getMaxReviewInterval());
        gen.writeNumberField(MIN_SPACE, configuration.getMinSpace());
        gen.writeNumberField(HARD_FACTOR, configuration.getHardFactor());
        gen.writeNumberField(PER_DAY, configuration.getCardsPerDay());
        gen.writeEndObject();
    }
}
