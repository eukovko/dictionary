package com.kovko.dictionary.mapper;

import com.kovko.dictionary.dto.Article;
import com.kovko.dictionary.dto.ArticleApiPresentation;
import com.kovko.dictionary.dto.ArticleNode;
import com.kovko.dictionary.dto.MiniCard;
import com.kovko.dictionary.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: eukovko
 * Date: 6/16/2020
 */
@Component
@RequiredArgsConstructor
public class Mapper {

    public final ModelMapper mapper;

    // to DTO
    public MiniCard toMiniCard(MiniCardEntity entity){
        return mapper.map(entity, MiniCard.class);
    }

    // to Entity
    public  MiniCardEntity toMiniCardEntity(MiniCard dto){
        return mapper.map(dto, MiniCardEntity.class);
    }

    public ArticleEntity toArticleEntity(ArticleApiPresentation dto){

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setArticleId(dto.getArticleId());
        articleEntity.setDictionary(dto.getDictionary());

        for (ArticleNode articleNode : dto.getBody()) {

            parseNode(articleEntity, articleNode);
        }
        return articleEntity;
    }

    private void parseNode(ArticleEntity articleEntity, ArticleNode articleNode) {

        if (articleNode instanceof ArticleNode.CardRefNode cardRefNode) {
            CardReferenceEntity cardReferenceEntity = new CardReferenceEntity();
            cardReferenceEntity.setArticleId(cardRefNode.getArticleId());
            cardReferenceEntity.setDictionary(cardRefNode.getDictionary());
            cardReferenceEntity.setText(cardRefNode.getText());
            List<MeaningEntity> meanings = articleEntity.getMeanings();
            if (meanings.isEmpty()) {
                MeaningEntity meaningEntity = new MeaningEntity();
                meaningEntity.setTitle(articleEntity.getTitle());
                meanings.add(meaningEntity);
            }
            int index = Math.max(meanings.size() - 1, 0);
            meanings.get(index).getCardReferenceEntities().add(cardReferenceEntity);
        }

        if (articleNode instanceof ArticleNode.ExampleNode exampleNode){
            ExampleEntity exampleEntity = new ExampleEntity();

            List<ArticleNode> items = exampleNode.getItems();
            if (items.get(0) instanceof ArticleNode.TextNode textNode) {
                exampleEntity.setExample(textNode.getText());
            }
            if (items.get(1) instanceof ArticleNode.TextNode textNode) {
                exampleEntity.setTranslation(textNode.getText());
            }
            List<MeaningEntity> meanings = articleEntity.getMeanings();
            if (meanings.isEmpty()) {
                MeaningEntity meaningEntity = new MeaningEntity();
                meaningEntity.setTitle(articleEntity.getTitle());
                meanings.add(meaningEntity);
            }
            int index = Math.max(meanings.size() - 1, 0);
            meanings.get(index).getExamples().add(exampleEntity);
        }

        if (articleNode instanceof ArticleNode.SoundNode soundNode){
            articleEntity.setSoundName(soundNode.getFileName());
        }

        if (articleNode instanceof ArticleNode.ParagraphNode paragraphNode){
            paragraphNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.ExamplesNode examplesNode){
            examplesNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.ExampleNode exampleNode){
            exampleNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.ExampleItemNode exampleItemNode){
            exampleItemNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.CardRefItemNode cardRefItemNode){
            cardRefItemNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.CardRefsNode cardRefsNode){
            cardRefsNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.ListNode listNode){
            listNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.ListItemNode listItemNode){

            if (listItemNode.getItems().get(0) instanceof ArticleNode.ParagraphNode paragraph){
                String meaning = "";
                for (ArticleNode item : paragraph.getItems()) {
                    if (item instanceof ArticleNode.TextNode textNode){
                        meaning = meaning.concat(textNode.getText());
                    }
                }
                MeaningEntity meaningEntity = new MeaningEntity();
                meaningEntity.setTitle(meaning);
                articleEntity.getMeanings().add(meaningEntity);
            }
            listItemNode.getItems().forEach(s->parseNode(articleEntity, s));
        }

        if (articleNode instanceof ArticleNode.CommentNode commentNode){
            commentNode.getItems().forEach(s->parseNode(articleEntity, s));
        }
    }

    public Article toArticle(ArticleEntity entity){
        return mapper.map(entity, Article.class);
    }
}
