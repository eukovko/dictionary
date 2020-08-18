package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.Field;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Author: eukovko
 * Date: 5/28/2020
 */
class FieldSerializerTest {

    static final String TEST_NAME = "Front";
    static final int TEST_ORDINAL = 0;

    String json = """
                   {
                     "name": "Front",
                     "media": [],
                     "sticky": false,
                     "rtl": false,
                     "ord": 0,
                     "font": "Arial",
                     "size": 20
                   }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new FieldSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Field serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        Field field = new Field(TEST_NAME, TEST_ORDINAL);
        JSONAssert.assertEquals(objectMapper.writeValueAsString(field), json, true);
    }
}