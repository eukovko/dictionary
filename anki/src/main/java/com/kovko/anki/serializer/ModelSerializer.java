package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Field;
import com.kovko.anki.json.Model;
import com.kovko.anki.json.Template;
import com.kovko.anki.json.util.GenerationRequirements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: eukovko
 * Date: 5/22/2020
 */
public class ModelSerializer extends StdSerializer<Model> {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String MODIFICATION_TIME = "mod";
    private static final String UPDATE_SEQUENCE_NUMBER = "usn";
    private static final String DEFAULT_DECK_ID = "did";
    private static final String TYPE = "type";
    private static final String SORT_FIELD = "sortf";
    private static final String LATEX_PREAMBLE = "latexPre";
    private static final String LATEX_POSTAMBLE = "latexPost";
    private static final String CSS = "css";
    private static final String LATEX_SVG = "latexsvg";
    private static final String VERSIONS = "vers";
    private static final String FIELDS = "flds";
    private static final String TEMPLATES = "tmpls";
    private static final String TAGS = "tags";
    private static final String REQUIRED_TO_GENERATE = "req";

    public ModelSerializer() {
        super(Model.class);
    }

    @Override
    public void serialize(Model model, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField(NAME, model.getName());
        gen.writeNumberField(ID, model.getId());

        gen.writeNumberField(MODIFICATION_TIME, model.getModificationTime());
        gen.writeNumberField(UPDATE_SEQUENCE_NUMBER, model.getUpdateSequenceNumber());

        gen.writeNumberField(DEFAULT_DECK_ID, model.getDefaultDeck().getId());
        gen.writeNumberField(TYPE, model.getType().getModelCode());

        gen.writeNumberField(SORT_FIELD, model.getSortField());
        gen.writeStringField(LATEX_PREAMBLE, model.getLatexPreamble());
        gen.writeStringField(LATEX_POSTAMBLE, model.getLatexPostamble());
        gen.writeStringField(CSS, model.getCss());
        gen.writeBooleanField(LATEX_SVG, model.isLatexSvg());

        gen.writeArrayFieldStart(VERSIONS);
        for (int version : Optional.ofNullable(model.getVersions()).orElse(new ArrayList<>())) {
            gen.writeNumber(version);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(FIELDS);
        for (Field field : model.getFields()) {
            gen.writeObject(field);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(TEMPLATES);
        for (Template template : model.getTemplates()) {
            gen.writeObject(template);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(TAGS);
        for (String tag : Optional.ofNullable(model.getTags()).orElse(new ArrayList<>())) {
            gen.writeString(tag);
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart(REQUIRED_TO_GENERATE);
        List<GenerationRequirements> requirements = model.getRequirements();
        for (int i = 0; i < requirements.size(); i++) {
            GenerationRequirements requirement = requirements.get(i);
            gen.writeStartArray();
            gen.writeNumber(i);
            gen.writeString(requirement.getGenerationType());
            gen.writeStartArray();
            for (Integer field : requirement.getFields()) {
                gen.writeNumber(field);
            }
            gen.writeEndArray();
            gen.writeEndArray();
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}
