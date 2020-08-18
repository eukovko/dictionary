package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.Template;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Author: eukovko
 * Date: 5/29/2020
 */
class TemplateSerializerTest {

    static final String TEST_NAME = "Card 1";

    String json = """
                  {
                    "name": "Card 1",
                    "qfmt": "{{Tags}}\\n<br><br>\\n{{Front}}",
                    "did": null,
                    "bafmt": "",
                    "afmt": "{{FrontSide}}\\n\\n<hr id=answer>\\n\\n{{Back}}",
                    "ord": 0,
                    "bqfmt": ""
                  }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new TemplateSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Template serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        Template template = new Template(TEST_NAME);
        JSONAssert.assertEquals(objectMapper.writeValueAsString(template), json, true);
    }
}