package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.DeckConfiguration;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class DeckConfigurationsSerializer extends StdSerializer<DeckConfiguration.DeckConfigurations> {

    public DeckConfigurationsSerializer() {
        super(DeckConfiguration.DeckConfigurations.class);
    }

    @Override
    public void serialize(DeckConfiguration.DeckConfigurations configurations,
                          JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        for (DeckConfiguration configuration : configurations.getDeckConfigurations()) {
            gen.writeObjectField(String.valueOf(configuration.getId()), configuration);
        }
        gen.writeEndObject();
    }
}
