package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Author: eukovko
 * Date: 5/22/2020
 */
public class FieldSerializer extends StdSerializer<Field> {

    private static final String NAME = "name";
    private static final String MEDIA = "media";
    private static final String STICKY = "sticky";
    private static final String RIGHT_TO_LEFT = "rtl";
    private static final String ORDINAL = "ord";
    private static final String FONT_NAME = "font";
    private static final String FONT_SIZE = "size";

    public FieldSerializer() {
        super(Field.class);
    }

    @Override
    public void serialize(Field field, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();

        gen.writeStringField(NAME, field.getName());
        gen.writeArrayFieldStart(MEDIA);
        for (String media : Optional.ofNullable(field.getMedia()).orElse(new ArrayList<>())) {
            gen.writeString(media);
        }
        gen.writeEndArray();
        gen.writeBooleanField(STICKY, field.isSticky());
        gen.writeBooleanField(RIGHT_TO_LEFT, field.isRightToLeft());
        gen.writeNumberField(ORDINAL, field.getOrdinal());
        gen.writeStringField(FONT_NAME, field.getFont().getName());
        gen.writeNumberField(FONT_SIZE, field.getFont().getSize());

        gen.writeEndObject();
    }
}
