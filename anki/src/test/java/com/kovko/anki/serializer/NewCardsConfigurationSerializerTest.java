package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.config.NewCardsConfiguration;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class NewCardsConfigurationSerializerTest {

    String json = """
            {
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
            }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new NewCardsConfigurationSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("New cards configuration serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        NewCardsConfiguration newCardsConfiguration = new NewCardsConfiguration();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(newCardsConfiguration), json, true);
    }
}