package com.kovko.dictionary.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/20/2020
 */
@Component
@Slf4j
public class MinicardTranslationProcessor implements Processor {


    @Override
    public void process(Exchange exchange) {

        String word = exchange.getIn().getBody(String.class);
        // TODO: 5/15/2020 Check with enum
        String srcLang = exchange.getIn().getHeader("srcLang", String.class);
        String dstLang = exchange.getIn().getHeader("dstLang", String.class);

        // TODO: 5/15/2020 Change to ints
        String header = String.format("text=%s&srcLang=%s&dstLang=%s", word, srcLang, dstLang);
        exchange.getIn().setHeader(Exchange.HTTP_QUERY, header);
        log.info("Minicard for : {} ", word);
    }
}
