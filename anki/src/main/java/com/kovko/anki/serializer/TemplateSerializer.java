package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Template;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/22/2020
 */
public class TemplateSerializer extends StdSerializer<Template> {

    private static final String NAME = "name";
    private static final String DECK_OVERRIDE_ID = "did";
    private static final String QUESTION_TEMPLATE = "qfmt";
    private static final String ANSWER_TEMPLATE = "afmt";
    private static final String BROWSER_QUESTION_TEMPLATE = "bqfmt";
    private static final String BROWSER_ANSWER_TEMPLATE = "bafmt";
    private static final String ORDINAL = "ord";

    public TemplateSerializer() {
        super(Template.class);
    }

    @Override
    public void serialize(Template template, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();

        gen.writeStringField(NAME, template.getName());

        if (template.getDeckOverride() == null) {
            gen.writeObjectField(DECK_OVERRIDE_ID, null);
        } else {
            gen.writeNumberField(DECK_OVERRIDE_ID, template.getDeckOverride().getId());
        }

        gen.writeStringField(QUESTION_TEMPLATE, template.getQuestionTemplate());
        gen.writeStringField(ANSWER_TEMPLATE, template.getAnswerTemplate());
        gen.writeStringField(BROWSER_QUESTION_TEMPLATE, template.getBrowserQuestionTemplate());
        gen.writeStringField(BROWSER_ANSWER_TEMPLATE, template.getBrowserAnswerTemplate());
        gen.writeNumberField(ORDINAL, template.getOrdinal());

        gen.writeEndObject();
    }
}
