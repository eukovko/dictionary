package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.Deck;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class DecksSerializerTest {

    static final String TEST_NAME = "Default";
    static final int TEST_ID = 1;
    static final long TEST_MODIFICATION_TIME = 1590562769L;

    String json = """
            {
              "1": {
                "newToday": [
                  0,
                  0
                ],
                "revToday": [
                  0,
                  0
                ],
                "lrnToday": [
                  0,
                  0
                ],
                "timeToday": [
                  0,
                  0
                ],
                "conf": 1,
                "usn": 0,
                "desc": "",
                "dyn": 0,
                "browserCollapsed": false,
                "collapsed": false,
                "extendNew": 10,
                "extendRev": 50,
                "id": 1,
                "name": "Default",
                "mod": 1590562769
              }
            }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new DecksSerializer());
        module.addSerializer(new DeckSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Deck serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        Deck deck = new Deck(TEST_NAME);
        deck.setId(TEST_ID);
        deck.setModificationTime(TEST_MODIFICATION_TIME);

        Deck.Decks decks = new Deck.Decks(new ArrayList<>(List.of(deck)));

        JSONAssert.assertEquals(objectMapper.writeValueAsString(decks), json, true);
    }
}