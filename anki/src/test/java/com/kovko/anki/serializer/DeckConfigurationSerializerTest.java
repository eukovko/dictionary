package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.DeckConfiguration;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class DeckConfigurationSerializerTest {

    static final String TEST_NAME = "Default";

    String json = """
            {
                "name": "Default",
                "new": {
                  "delays": [
                    1,
                    10
                  ],
                  "ints": [
                    1,
                    4,
                    7
                  ],
                  "initialFactor": 2500,
                  "separate": true,
                  "order": 1,
                  "perDay": 50,
                  "bury": false
                },
                "lapse": {
                  "delays": [
                    10
                  ],
                  "mult": 0,
                  "minInt": 1,
                  "leechFails": 8,
                  "leechAction": 0
                },
                "rev": {
                  "perDay": 200,
                  "ease4": 1.3,
                  "fuzz": 0.05,
                  "minSpace": 1,
                  "ivlFct": 1,
                  "maxIvl": 36500,
                  "bury": false,
                  "hardFactor": 1.2
                },
                "maxTaken": 60,
                "timer": 0,
                "autoplay": true,
                "replayq": true,
                "mod": 0,
                "usn": 0,
                "id": 1
             }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new DeckConfigurationSerializer());
        module.addSerializer(new ReviewCardsConfigurationSerializer());
        module.addSerializer(new NewCardsConfigurationSerializer());
        module.addSerializer(new LapsedCardsConfigurationSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Deck configuration serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        DeckConfiguration deckConfiguration = new DeckConfiguration(TEST_NAME);
        JSONAssert.assertEquals(objectMapper.writeValueAsString(deckConfiguration), json, true);
    }
}