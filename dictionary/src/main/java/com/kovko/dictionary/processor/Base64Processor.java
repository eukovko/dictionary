package com.kovko.dictionary.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Author: eukov
 * Date: 2/23/2020
 */
@Slf4j
@Component
public class Base64Processor implements Processor {

    @Override
    public void process(Exchange exchange) {
        String file = exchange.getMessage().getBody(String.class).replace("\"","");
        byte[] audio = Base64.getDecoder().decode(file);
        log.info("Base64 processor: {}", audio.length);
        exchange.getMessage().setBody(audio);
    }
}
