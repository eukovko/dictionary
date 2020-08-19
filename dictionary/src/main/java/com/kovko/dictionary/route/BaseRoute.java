package com.kovko.dictionary.route;

import com.kovko.dictionary.processor.HttpOperationFailedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BaseRoute extends RouteBuilder {

    private final HttpOperationFailedProcessor httpOperationFailedProcessor;

    @Override
    public void configure() throws Exception {

        onException(HttpOperationFailedException.class)
                // TODO: 6/23/2020 Redelivery pattern?
                .redeliveryDelay(0)
                .maximumRedeliveries(3)
                .onRedelivery(httpOperationFailedProcessor)
                // TODO: 6/23/2020 Set to false?
                .handled(true);
    }
}
