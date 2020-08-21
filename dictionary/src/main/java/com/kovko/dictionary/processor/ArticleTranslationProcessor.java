package com.kovko.dictionary.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 7/26/2020
 */
@Component
@Slf4j
public class ArticleTranslationProcessor implements Processor {


    @Override
    public void process(Exchange exchange) {

        String word = exchange.getIn().getBody(String.class);
        // TODO: 5/15/2020 Check with enum
        Integer srcLang = exchange.getIn().getHeader("srcLang", Integer.class);
        Integer dstLang = exchange.getIn().getHeader("dstLang", Integer.class);
        String dictionary = exchange.getIn().getHeader("dict", String.class);

        // TODO: 5/15/2020 Change to ints
        String header = String.format("heading=%s&dict=%s&srcLang=%d&dstLang=%d", word, dictionary, srcLang, dstLang);
        exchange.getIn().setHeader(Exchange.HTTP_QUERY, header);
        log.info("Article for {} in {} dictionary ", word, dictionary);
    }
}
