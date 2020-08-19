package com.kovko.dictionary.processor;

import com.kovko.dictionary.entity.ArticleEntity;
import com.kovko.dictionary.entity.MiniCardEntity;
import com.kovko.dictionary.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Author: eukovko
 * Date: 6/16/2020
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleDatabaseProcessor implements Processor {

    private static final String DICTIONARY_NAME = "LingvoUniversal (Ru-En)";
    private final ArticleRepository articleRepository;

    @Override
    public void process(Exchange exchange) {

        Message message = exchange.getMessage();
        String title = message.getBody(String.class);
        Integer sourceLanguage = message.getHeader("srcLang", Integer.class);
        Integer targetLanguage = message.getHeader("dstLang", Integer.class);

        log.info("Trying to find the word in the database: {} {} {}", title, sourceLanguage, targetLanguage);
        Optional<ArticleEntity> articleEntity = articleRepository.findByTitleAndDictionary(title, DICTIONARY_NAME);

        if (articleEntity.isPresent()) {
            log.info("Article exists");
            message.setHeader("translationExists", true);
            message.setBody(articleEntity.get(), MiniCardEntity.class);
        } else {
            log.info("Article doesn't exists");
            message.setHeader("translationExists", false);
        }

    }
}
