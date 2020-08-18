package com.kovko.anki.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: eukovko
 * Date: 6/21/2020
 */
@Getter
@Setter
@AllArgsConstructor
public class Media {

    /**
     * The name of the media file
     */
    private String fileName;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class MediaFiles {

        private List<Media> mediaFiles;
    }
}
