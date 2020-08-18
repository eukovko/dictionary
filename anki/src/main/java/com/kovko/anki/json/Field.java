package com.kovko.anki.json;

import com.kovko.anki.json.util.Font;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
public class Field {

    /**
     * Field name
     */
    private String name;

    /**
     * Field font
     */
    private Font font = new Font("Arial", 20);

    /**
     * This field currently unused
     * Empty array by default
     */
    private List<String> media;

    /**
     * Defines if the value of the field of the last added note
     * will be saved and automatically filled when adding the next note
     */
    private boolean sticky = false;

    /**
     * Sets right to left format for some languages
     */
    private boolean rightToLeft = false;

    /**
     * Fields ordinal number
     */
    // TODO: 5/19/2020 Maybe use the index of the field in an array
    private int ordinal;

    public Field(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }
}
