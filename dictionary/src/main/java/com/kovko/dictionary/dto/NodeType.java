package com.kovko.dictionary.dto;

import lombok.Getter;

/**
 * Author: eukovko
 * Date: 6/30/2020
 */
@Getter
public enum NodeType {

    COMMENT("Comment", ArticleNode.CommentNode.class),
    PARAGRAPH("Paragraph", ArticleNode.ParagraphNode.class),
    TEXT("Text", ArticleNode.TextNode.class),
    LIST("List", ArticleNode.ListNode.class),
    LIST_ITEM("ListItem", ArticleNode.ListItemNode.class),
    EXAMPLES("Examples", ArticleNode.ExamplesNode.class),
    EXAMPLE_ITEM("ExampleItem", ArticleNode.ExampleItemNode.class),
    EXAMPLE("Example", ArticleNode.ExampleNode.class),
    CARD_REFS("CardRefs", ArticleNode.CardRefsNode.class),
    CARD_REF_ITEM("CardRefItem", ArticleNode.CardRefItemNode.class),
    CARD_REF("CardRef", ArticleNode.CardRefNode.class),
    TRANSCRIPTION("Transcription", ArticleNode.TranscriptionNode.class),
    ABBREV("Abbrev", ArticleNode.AbbrevNode.class),
    CAPTION("Caption", ArticleNode.CaptionNode.class),
    SOUND("Sound", ArticleNode.SoundNode.class),
    REF("Ref", ArticleNode.RefNode.class),
    UNSUPPORTED("Unsupported", ArticleNode.UnsupportedNode.class);

    private final String typeName;
    private final Class<?> type;

    NodeType(String typeName, Class<?> type) {
        this.typeName = typeName;
        this.type = type;
    }

}
