package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kovko.anki.json.Media;

import java.io.IOException;
import java.util.List;

/**
 * Author: eukovko
 * Date: 6/21/2020
 */
public class MediaFilesSerializer extends StdSerializer<Media.MediaFiles> {

    public MediaFilesSerializer() {
        super(Media.MediaFiles.class);
    }

    @Override
    public void serialize(Media.MediaFiles mediaFiles, JsonGenerator gen, SerializerProvider serializerProvider)
            throws IOException {

        List<Media> files = mediaFiles.getMediaFiles();
        gen.writeStartObject();
        for (int i = 0; i < files.size(); i++) {
            gen.writeStringField(String.valueOf(i), files.get(i).getFileName());
        }
        gen.writeEndObject();

    }
}
