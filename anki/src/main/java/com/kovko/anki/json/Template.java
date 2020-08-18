package com.kovko.anki.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
// TODO: 5/19/2020 Add the list of Anki tags
public class Template {

    /**
     * Card template name.
     * In most cases just a simple name (Card1, Card2, Cloze, etc.)
     */
    // TODO: 5/19/2020 Add the naming logic to model
    private String name;

    // TODO: 5/19/2020 Thymeleaf template can be used for this (HTML, JS, CSS) and additional Anki specific tags
    /**
     * Template of the question card for this template
     */
    private String questionTemplate = "{{Front}}{{Audio}}";
    /**
     * Template of the answer card for this template
     */
    private String answerTemplate = "{{Back}}";

    /**
     * Override for browser appearance for an answer template
     * Empty by default, so browser will not override representation
     */
    private String browserAnswerTemplate = "";

    /**
     * Override for browser appearance for a question template
     * Empty by default, so browser will not override representation
     */
    // TODO: 5/19/2020 For some reason this override doesn't work in preferences
    private String browserQuestionTemplate = "";

    /**
     * Define the the deck where the card will be placed
     * Default to null if this card template doesn't override note deck id
     */
    private Deck deckOverride = null;

    /**
     * Define the ordinal number of a card which correlate with this representation
     */
    private int ordinal = 0;

    public Template(String name) {
        this.name = name;
    }
}
