package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Deck;

import java.io.IOException;

/**
 * Author: eukovko
 * Date: 5/23/2020
 */
public class DecksSerializer extends StdSerializer<Deck.Decks> {

    public DecksSerializer() {
        super(Deck.Decks.class);
    }

    @Override
    public void serialize(Deck.Decks decks, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        for (Deck deck : decks.getDecks()) {
           gen.writeObjectField(String.valueOf(deck.getId()), deck);
        }
        gen.writeEndObject();
    }
}
