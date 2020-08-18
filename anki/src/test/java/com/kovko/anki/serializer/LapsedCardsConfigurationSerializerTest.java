package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.config.LapsedCardsConfiguration;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class LapsedCardsConfigurationSerializerTest {

    String json = """
            {
              "delays": [
                10
              ],
              "mult": 0,
              "minInt": 1,
              "leechFails": 8,
              "leechAction": 0
            }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new LapsedCardsConfigurationSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Lapsed card configuration serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        LapsedCardsConfiguration lapsedCardsConfiguration = new LapsedCardsConfiguration();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(lapsedCardsConfiguration), json, true);
    }
}