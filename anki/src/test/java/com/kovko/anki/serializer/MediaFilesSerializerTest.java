package com.kovko.anki.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.Media;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: eukovko
 * Date: 6/21/2020
 */
class MediaFilesSerializerTest {

    String json = "{\n" +
            "  \"0\": \"test1.wav\",\n" +
            "  \"1\": \"test2.wav\"\n" +
            "}";

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new MediaFilesSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Media files serializer test")
    void testSerializer() throws JsonProcessingException, JSONException {

        List<Media> files = List.of("test1.wav", "test2.wav").stream().map(Media::new).collect(Collectors.toList());
        Media.MediaFiles mediaFiles = new Media.MediaFiles(files);
        JSONAssert.assertEquals(objectMapper.writeValueAsString(mediaFiles), json, true);
    }
}