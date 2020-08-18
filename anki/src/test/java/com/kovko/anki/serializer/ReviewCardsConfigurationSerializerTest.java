package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.config.ReviewCardsConfiguration;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class ReviewCardsConfigurationSerializerTest {

    String json = """
            {
              "perDay": 200,
              "ease4": 1.3,
              "fuzz": 0.05,
              "minSpace": 1,
              "ivlFct": 1,
              "maxIvl": 36500,
              "bury": false,
              "hardFactor": 1.2
            }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new ReviewCardsConfigurationSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Review cards configuration serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        ReviewCardsConfiguration reviewCardsConfiguration = new ReviewCardsConfiguration();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(reviewCardsConfiguration), json, true);
    }
}