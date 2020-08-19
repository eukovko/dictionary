package com.kovko.dictionary.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * Author: eukovko
 * Date: 6/29/2020
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Node")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArticleNode.SoundNode.class, name = "Sound"),
        @JsonSubTypes.Type(value = ArticleNode.CaptionNode.class, name = "Caption"),
        @JsonSubTypes.Type(value = ArticleNode.CommentNode.class, name = "Comment"),
        @JsonSubTypes.Type(value = ArticleNode.ParagraphNode.class, name = "Paragraph"),
        @JsonSubTypes.Type(value = ArticleNode.AbbrevNode.class, name = "Abbrev"),
        @JsonSubTypes.Type(value = ArticleNode.ListNode.class, name = "List"),
        @JsonSubTypes.Type(value = ArticleNode.ListItemNode.class, name = "ListItem"),
        @JsonSubTypes.Type(value = ArticleNode.ExamplesNode.class, name = "Examples"),
        @JsonSubTypes.Type(value = ArticleNode.ExampleItemNode.class, name = "ExampleItem"),
        @JsonSubTypes.Type(value = ArticleNode.ExampleNode.class, name = "Example"),
        @JsonSubTypes.Type(value = ArticleNode.CardRefsNode.class, name = "CardRefs"),
        @JsonSubTypes.Type(value = ArticleNode.CardRefItemNode.class, name = "CardRefItem"),
        @JsonSubTypes.Type(value = ArticleNode.CardRefNode.class, name = "CardRef"),
        @JsonSubTypes.Type(value = ArticleNode.TranscriptionNode.class, name = "Transcription"),
        @JsonSubTypes.Type(value = ArticleNode.TextNode.class, name = "Text")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class ArticleNode {

    // TODO: 6/29/2020 Substitute by enum

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class TextNode extends ArticleNode {

        @JsonProperty("IsItalics")
        private boolean italics;
        @JsonProperty("IsAccent")
        private boolean accent;
        private String text;
        @JsonProperty("IsOptional")
        private boolean optional;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class ParagraphNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class CommentNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class UnsupportedNode extends ArticleNode {
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class CaptionNode extends ArticleNode {
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class RefNode extends ArticleNode {
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class TranscriptionNode extends ArticleNode {
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class ListNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class ListItemNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class SoundNode extends ArticleNode {

        private String fileName;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class AbbrevNode extends ArticleNode {

        private String text;
        private String fullText;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class ExamplesNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class ExampleItemNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class ExampleNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class CardRefsNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class CardRefItemNode extends ArticleNode {

        @JsonAlias("Markup")
        private List<ArticleNode> items;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    public static class CardRefNode extends ArticleNode {

        private String dictionary;
        private String articleId;
        private String text;
    }

}
