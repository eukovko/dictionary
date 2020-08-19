package com.kovko.dictionary.route;

import com.kovko.dictionary.aggregator.AuthenticationAggregator;
import com.kovko.dictionary.processor.AuthenticationProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Produce;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.apache.camel.builder.Builder.constant;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author: eukovko
 * Date: 6/13/2020
 */
@Slf4j
@CamelSpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {AuthenticationRoute.class, AuthenticationAggregator.class, AuthenticationProcessor.class}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthenticationRouteTest {

    static final String TOKEN = "testToken";

    @Produce("direct:authenticate")
    private FluentProducerTemplate producerTemplate;

    @EndpointInject("mock:output")
    MockEndpoint mockEndpoint;

    @Autowired
    AuthenticationProcessor authenticationProcessor;

    @BeforeEach
    public void setUp(@Autowired CamelContext context) throws Exception {

        AdviceWithRouteBuilder.adviceWith(context, "direct.authenticate", s -> s.weaveAddLast().to("mock:output"));

        AdviceWithRouteBuilder.adviceWith(context, "api.authentication.input",
                s -> s.weaveByToUri("{{api.authentication.endpoint}}").replace().transform(constant(String.format("\"%s\"", TOKEN))));
    }

    @Test
    void testContext() {
        assertThat(producerTemplate).isNotNull();
    }

    @Test
    void testAuthentication() throws InterruptedException {

        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedHeaderReceived(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", TOKEN));

        producerTemplate.request();

        mockEndpoint.assertIsSatisfied();

        assertThat(authenticationProcessor.getAuthenticationToken()).isEqualTo(TOKEN);
    }
}