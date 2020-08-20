package com.kovko.dictionary.processor;

import com.kovko.dictionary.util.LingvoLanguage;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: eukovko
 * Date: 6/20/2020
 */
@Component
@Slf4j
public class FileProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String text = exchange.getIn().getBody(String.class);
        // TODO: 5/15/2020 Change header name to ENUM
        String filename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        String baseName = FilenameUtils.getBaseName(filename);
        int length = baseName.length();
        String sourceLanguage = baseName.substring(length - 5, length-3);
        String targetLanguage = baseName.substring(length - 2);

        LingvoLanguage srcLang = LingvoLanguage.valueOf(sourceLanguage);
        LingvoLanguage dstLang = LingvoLanguage.valueOf(targetLanguage);

        log.info("Translate text file from {} to {}", sourceLanguage, targetLanguage);

        log.info("Text {}", text);
        List<String> wordList = text.lines().collect(Collectors.toList());

        wordList.forEach(s->log.info("Word to translate {}", s));

        exchange.getIn().setHeader("srcLang", srcLang.getLingvoCode());
        exchange.getIn().setHeader("dstLang", dstLang.getLingvoCode());
        exchange.getIn().setBody(wordList);
    }
}
