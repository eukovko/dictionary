package com.kovko.anki.json;

import com.kovko.anki.json.util.GenerationRequirements;
import com.kovko.anki.json.util.ModelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
public class Model {

    /**
     * The time of creation in epoch milliseconds
     */
    private long id = Instant.now().toEpochMilli();
    /**
     * The name of the model.
     * Usually just describes it's behaviour (Basic, Basic with reverse, Cloze, etc.)
     */
    private String name;
    /**
     * The time of modification in epoch seconds
     */
    private long modificationTime = Instant.now().getEpochSecond();

    /**
     * Defines if the model has changes and need to be updated
     */
    private int updateSequenceNumber = -1;

    /**
     * CSS styles for all underlying templates
     */
    private String css = ".card {\n" +
            " font-family: arial;\n" +
            " font-size: 20px;\n" +
            " text-align: center;\n" +
            " color: black;\n" +
            " background-color: white;\n" +
            "}\n\n";
    /**
     * Deck where new notes are stored by default
     * Can be overridden in {@link Template} for each card separately
     */
    private Deck defaultDeck;

    /**
     * List of all the field of the model
     */
    private List<Field> fields = new ArrayList<>(List.of(
                    new Field("Front", 0),
                    new Field("Audio", 1),
                    new Field("Back", 2)));

    // TODO: 5/17/2020 Set to some default values
    /**
     * String which will be appended to LaTeX expression
     */
    private String latexPostamble = "\\end{document}";
    /**
     * String which will be prepended to LaTeX expression
     */
    private String latexPreamble = "\\documentclass[12pt]{article}" +
            "\n\\special{papersize=3in,5in}" +
            "\n\\usepackage[utf8]{inputenc}" +
            "\n\\usepackage{amssymb,amsmath}" +
            "\n\\pagestyle{empty}" +
            "\n\\setlength{\\parindent}{0in}" +
            "\n\\begin{document}\n";

    // TODO: 5/23/2020 Find out what this field does
    private boolean latexSvg = false;

    /**
     * Rules to generate the cards
     * https://github.com/Arthur-Milchior/anki/blob/commented/documentation//templates_generation_rules.md
     */
    // TODO: 5/23/2020 Maybe move to fields, need to check the documentation
    private List<GenerationRequirements> requirements =
            new ArrayList<>(
                    List.of(new GenerationRequirements("any", new ArrayList<>(List.of(0))),
                            new GenerationRequirements("any", new ArrayList<>(List.of(0)))));

    /**
     * Ordinal number of the field which is used for sorting in application browser
     */
    private int sortField = 0;

    /**
     * The tags of the last saved note
     * This tags will be filled automatically
     * in the tag field while adding a new note
     */
    private List<String> tags = null;

    /**
     * Templates for model's cards
     * Each template will create a card from a note
     */
    private List<Template> templates;

    /**
     * The type of the model
     */
    private ModelType type = ModelType.STANDARD;


    /**
     * This field currently isn't in use
     */
    private List<Integer> versions = null;

    public Model(String name) {
        this.name = name;
        initTemplates();
    }

    /**
     * Author: eukovko
     * Date: 5/31/2020
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Models {

        private List<Model> models;
    }

    private void initTemplates(){
        Template direct = new Template("Direct");
        String frontSide = "{{FrontSide}}<hr id=answer>";
        direct.setAnswerTemplate(frontSide.concat(direct.getAnswerTemplate()));

        Template reverse = new Template("Reverse");
        String answerTemplate = reverse.getAnswerTemplate();
        String questionTemplate = reverse.getQuestionTemplate();
        reverse.setAnswerTemplate(frontSide.concat(questionTemplate));
        reverse.setQuestionTemplate(answerTemplate);
        reverse.setOrdinal(1);

        this.templates = new ArrayList<>(List.of(reverse,direct));
    }
}
