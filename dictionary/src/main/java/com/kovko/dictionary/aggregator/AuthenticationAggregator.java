package com.kovko.dictionary.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/13/2020
 */
@Slf4j
@Component
public class AuthenticationAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        String token = newExchange.getIn().getBody(String.class);
        log.info("Requested a new token\nNew token: {}\n", token);
        oldExchange.getIn().setHeader("Authorization", String.format("Bearer %s", token));
        oldExchange.getIn().setBody(token);
        return oldExchange;
    }
}
