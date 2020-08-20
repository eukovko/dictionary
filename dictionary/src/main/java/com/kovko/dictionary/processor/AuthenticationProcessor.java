package com.kovko.dictionary.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/13/2020
 */
@Component
@Slf4j
public class AuthenticationProcessor implements Processor {

    private static final String BEARER_TYPE_FORMAT = "Bearer %s";

    private String authenticationToken;

    @Override
    public void process(Exchange exchange) {

        authenticationToken = stripQuotations(exchange.getIn().getBody(String.class));
        exchange.getIn().setBody(authenticationToken);
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public String getAuthorizationHeader() {
        return String.format(BEARER_TYPE_FORMAT, getAuthenticationToken());
    }

    static String stripQuotations(String token) {
        return token.substring(1, token.length() - 1);
    }
}
