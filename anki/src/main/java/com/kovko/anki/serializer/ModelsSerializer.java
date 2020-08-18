package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Model;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class ModelsSerializer extends StdSerializer<Model.Models> {

    public ModelsSerializer() {
        super(Model.Models.class);
    }

    @Override
    public void serialize(Model.Models models, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        for (Model model : models.getModels()) {
            gen.writeObjectField(String.valueOf(model.getId()), model);
        }
        gen.writeEndObject();
    }
}
