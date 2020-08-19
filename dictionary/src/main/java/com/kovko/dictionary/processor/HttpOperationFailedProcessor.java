package com.kovko.dictionary.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpOperationFailedProcessor implements Processor {

    private static final String EXCEPTION_CAUGHT = "CamelExceptionCaught";
    private final FluentProducerTemplate template;

    @Override
    public void process(Exchange exchange) throws InterruptedException {

        HttpOperationFailedException exception =
                exchange.getProperty(EXCEPTION_CAUGHT, HttpOperationFailedException.class);
        int statusCode = exception.getStatusCode();
        log.info("Handle {} Http response status", statusCode);
        // TODO: 5/2/2020 It's possible to throw an Exception from here
        // TODO: 5/15/2020 Set header to skip additional requests and set error handled param
        switch (statusCode) {
            case HttpStatus.SC_UNAUTHORIZED -> template.to("direct:authenticate").withExchange(exchange).send();
            case HttpStatus.SC_NOT_FOUND -> {
                // TODO: 7/26/2020 Think about mutual object which will have this response regardless endpoint
                log.info("Ignoring 404 error");
                String unknownWords = exchange.getIn().getHeader("unknownWords", String.class);
                if (unknownWords==null){
                    exchange.getIn().setHeader("unknownWords", "!");
                    unknownWords = "!";
                }
                exchange.getIn().setHeader("unknownWords", unknownWords.concat("!"));
            }
            case 429 -> Thread.currentThread().wait(60*1000);
            default -> log.info("Unsupported status code: {}", statusCode);
        }
    }

}
