package com.kovko.dictionary.processor;

import com.kovko.dictionary.dto.ImportDeck;
import com.kovko.dictionary.dto.TranslationBatch;
import com.kovko.dictionary.util.LingvoLanguage;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: eukovko
 * Date: 7/28/2020
 */
@Component
@Slf4j
public class ImportDeckProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        ImportDeck importDeck = exchange.getIn().getBody(ImportDeck.class);
        TranslationBatch batch = importDeck.getTranslationBatch();
        LingvoLanguage sourceLanguage = batch.getSourceLanguage();
        LingvoLanguage targetLanguage = batch.getTargetLanguage();

        log.info("Translate text file from {} to {}", sourceLanguage, targetLanguage);

        List<String> wordList = batch.getWords();

        wordList.forEach(s->log.info("Word to translate {}", s));

        exchange.getIn().setHeader("srcLang", sourceLanguage.getLingvoCode());
        exchange.getIn().setHeader("dstLang", targetLanguage.getLingvoCode());
        exchange.getIn().setBody(wordList);
    }
}
