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

/**
 * Author: eukovko
 * Date: 5/30/2020
 */
class DeckSerializerTest {

    static final int TEST_ID = 1;
    static final String TEST_NAME = "Default";
    static final int TEST_MODIFICATION_TIME = 1590562769;

    String json = """
            {
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
              "collapsed": false,
              "browserCollapsed": false,
              "extendNew": 10,
              "extendRev": 50,
              "id": 1,
              "name": "Default",
              "mod": 1590562769
            }            
            """;

    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
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

        // TODO: 5/30/2020 Add length array assertion and remove collapse browser
        JSONAssert.assertEquals(objectMapper.writeValueAsString(deck), json, true);
    }
}