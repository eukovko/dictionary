package com.kovko.dictionary.route;

import com.kovko.dictionary.aggregator.AuthenticationAggregator;
import com.kovko.dictionary.processor.AuthenticationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationRoute extends RouteBuilder {

    private final AuthenticationProcessor authenticationProcessor;
    private final AuthenticationAggregator authenticationAggregator;

    @Override
    public void configure() {

        from("direct:authenticate")
                .routeId("direct.authenticate")
                .enrich("{{api.authentication.input}}", authenticationAggregator);

        from("{{api.authentication.input}}").routeId("api.authentication.input")
                .setHeader(HttpHeaders.AUTHORIZATION, simple("Basic {{api.access.key}}"))
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.TEXT_PLAIN_VALUE))
                .to("{{api.authentication.endpoint}}")
                .process(authenticationProcessor);

    }
}
