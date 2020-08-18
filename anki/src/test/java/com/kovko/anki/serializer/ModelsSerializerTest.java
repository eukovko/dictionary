package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.Deck;
import com.kovko.anki.json.Field;
import com.kovko.anki.json.Model;
import com.kovko.anki.json.Template;
import com.kovko.anki.json.util.GenerationRequirements;
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
class ModelsSerializerTest {

    static final String TEST_DECK_NAME = "Default";
    static final long TEST_DECK_ID = 1590562734055L;
    static final String TEST_TEMPLATE_NAME = "Card 1";
    static final String TEST_FIRST_FIELD_NAME = "Front";
    static final int TEST_FIRST_FIELD_ORDINAL = 0;
    static final String TEST_SECOND_FIELD_NAME = "Back";
    static final int TEST_SECOND_FIELD_ORDINAL = 1;
    static final String TEST_GENERATION_TYPE = "any";
    static final List<Integer> TEST_FIELDS = List.of(0);
    static final String TEST_MODEL_NAME = "Basic";
    static final long TEST_MODEL_ID = 1536061496854L;
    static final long TEST_MODEL_MODIFICATION_TIME = 1590562746L;

    String json = """
            {
              "1536061496854": {
                "vers": [],
                "name": "Basic",
                "tags": [],
                "did": 1590562734055,
                "usn": -1,
                "req": [
                  [
                    0,
                    "any",
                    [
                      0
                    ]
                  ]
                ],
                "flds": [
                  {
                    "name": "Front",
                    "media": [],
                    "sticky": false,
                    "rtl": false,
                    "ord": 0,
                    "font": "Arial",
                    "size": 20
                  },
                  {
                    "name": "Back",
                    "media": [],
                    "sticky": false,
                    "rtl": false,
                    "ord": 1,
                    "font": "Arial",
                    "size": 20
                  }
                ],
                "latexsvg": false,
                "sortf": 0,
                "tmpls": [
                  {
                    "name": "Card 1",
                    "qfmt": "{{Tags}}\\n<br><br>\\n{{Front}}",
                    "did": null,
                    "bafmt": "",
                    "afmt": "{{FrontSide}}\\n\\n<hr id=answer>\\n\\n{{Back}}",
                    "ord": 0,
                    "bqfmt": ""
                  }
                ],
                "mod": 1590562746,
                "latexPost": "\\\\end{document}",
                "type": 0,
                "id": 1536061496854,
                "css": ".card {\\n font-family: arial;\\n font-size: 20px;\\n text-align: left;\\n color: black;\\n background-color: white;\\n}\\n",
                "latexPre": "\\\\documentclass[12pt]{article}\\n\\\\special{papersize=3in,5in}\\n\\\\usepackage[utf8]{inputenc}\\n\\\\usepackage{amssymb,amsmath}\\n\\\\pagestyle{empty}\\n\\\\setlength{\\\\parindent}{0in}\\n\\\\begin{document}\\n"
              }
            }
            """;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new ModelsSerializer());
        module.addSerializer(new ModelSerializer());
        module.addSerializer(new TemplateSerializer());
        module.addSerializer(new FieldSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Models serizlizer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        Deck deck = new Deck(TEST_DECK_NAME);
        deck.setId(TEST_DECK_ID);

        Template template = new Template(TEST_TEMPLATE_NAME);
        Field front = new Field(TEST_FIRST_FIELD_NAME, TEST_FIRST_FIELD_ORDINAL);
        Field back = new Field(TEST_SECOND_FIELD_NAME, TEST_SECOND_FIELD_ORDINAL);
        GenerationRequirements generationRequirements = new GenerationRequirements(TEST_GENERATION_TYPE, TEST_FIELDS);

        Model model = new Model(TEST_MODEL_NAME);
        model.setId(TEST_MODEL_ID);
        model.setModificationTime(TEST_MODEL_MODIFICATION_TIME);
        model.setDefaultDeck(deck);
        model.setTemplates(List.of(template));
        model.setRequirements(List.of(generationRequirements));
        model.setFields(List.of(front, back));

        Model.Models models = new Model.Models(new ArrayList<>(List.of(model)));

        JSONAssert.assertEquals(objectMapper.writeValueAsString(models), json, true);

    }
}