package com.kovko.dictionary.processor;

import com.kovko.dictionary.entity.MiniCardEntity;
import com.kovko.dictionary.repository.MinicardRepository;
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
public class MiniCardDatabaseProcessor implements Processor {

    private final MinicardRepository minicardRepository;

    @Override
    public void process(Exchange exchange) {

        Message message = exchange.getMessage();
        String heading = message.getBody(String.class);
        Integer sourceLanguage = message.getHeader("srcLang", Integer.class);
        Integer targetLanguage = message.getHeader("dstLang", Integer.class);

        log.info("Trying to find the word in the database: {} {} {}", heading, sourceLanguage, targetLanguage);
        Optional<MiniCardEntity> miniCardEntity =
                minicardRepository.findByHeadingAndSourceLanguageAndTargetLanguage(
                        heading,sourceLanguage, targetLanguage);

        if (miniCardEntity.isPresent()) {
            log.info("Translation exists");
            message.setHeader("translationExists", true);
            message.setBody(miniCardEntity.get(), MiniCardEntity.class);
        } else {
            log.info("Translation doesn't exists");
            message.setHeader("translationExists", false);
        }

    }
}
