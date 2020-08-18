package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.CollectionConfiguration;
import com.kovko.anki.json.Deck;
import com.kovko.anki.json.Model;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class CollectionConfigurationSerializerTest {

    static final String TEST_DECK_NAME = "Default";
    static final String TEST_MODEL_NAME = "Default";
    static final int TEST_DECK_ID = 1;
    static final long TEST_MODEL_ID = 1590562770084L;

    String json = """
            {
              "activeDecks": [
                1
              ],
              "curDeck": 1,
              "newSpread": 0,
              "collapseTime": 1200,
              "timeLim": 0,
              "estTimes": true,
              "dueCounts": true,
              "curModel": "1590562770084",
              "nextPos": 1,
              "sortType": "noteFld",
              "sortBackwards": false,
              "addToCur": true,
              "dayLearnFirst": false,
              "schedVer": 1,
              "newBury": true
            }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new CollectionConfigurationSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Collection configuration serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        // TODO: 5/30/2020 Add active decks automatically
        CollectionConfiguration collectionConfiguration = new CollectionConfiguration();
        Deck deck = new Deck(TEST_DECK_NAME);
        deck.setId(TEST_DECK_ID);
        Model model = new Model(TEST_MODEL_NAME);
        model.setId(TEST_MODEL_ID);

        collectionConfiguration.setCurrentModel(model);
        collectionConfiguration.setCurrentDeck(deck);
        collectionConfiguration.setActiveDecks(List.of(deck));

        JSONAssert.assertEquals(objectMapper.writeValueAsString(collectionConfiguration), json, true);
    }
}