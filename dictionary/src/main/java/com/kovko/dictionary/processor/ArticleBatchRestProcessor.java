package com.kovko.dictionary.processor;

import com.kovko.dictionary.dto.ArticleTranslationBatch;
import com.kovko.dictionary.dto.MinicardTranslationBatch;
import com.kovko.dictionary.util.LingvoLanguage;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: eukovko
 * Date: 7/24/2020
 */
@Component
@Slf4j
public class ArticleBatchRestProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {

        ArticleTranslationBatch batch = exchange.getIn().getBody(ArticleTranslationBatch.class);
        LingvoLanguage sourceLanguage = batch.getSourceLanguage();
        LingvoLanguage targetLanguage = batch.getTargetLanguage();
        String dictionary = batch.getDictionary();

        log.info("Translate text file from {} to {}", sourceLanguage, targetLanguage);

        List<String> wordList = batch.getWords();

        wordList.forEach(s->log.info("Word to translate {}", s));

        exchange.getIn().setHeader("srcLang", sourceLanguage.getLingvoCode());
        exchange.getIn().setHeader("dstLang", targetLanguage.getLingvoCode());
        exchange.getIn().setHeader("dict", dictionary);
        exchange.getIn().setBody(wordList);
    }
}
