package com.kovko.dictionary.route;

import com.kovko.dictionary.aggregator.AuthenticationAggregator;
import com.kovko.dictionary.config.CamelConfig;
import com.kovko.dictionary.processor.AuthenticationProcessor;
import com.kovko.dictionary.processor.HttpOperationFailedProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Slf4j
@CamelSpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {MiniCardRoute.class, HttpOperationFailedProcessor.class, AuthenticationRoute.class, AuthenticationAggregator.class, AuthenticationProcessor.class}, initializers = ConfigFileApplicationContextInitializer.class)
@Import(CamelConfig.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MiniCardRouteTest {

    @EndpointInject("mock:output")
    MockEndpoint mockEndpoint;

    @Autowired
    DataFormat dataFormat;

    @Test
    void testMiniCardRestEndpoint(@Autowired CamelContext context) throws Exception {

        AdviceWithRouteBuilder.adviceWith(context, "dictionary", s -> s.weaveByToUri("{{api.translation.minicard.input}}").replace().to("mock:output"));

        RestTemplate restTemplate = new RestTemplate();

        int statusCode = restTemplate.getForEntity("http://localhost:9090/hello?text=hello&srcLang=1033&dstLang=1049", String.class)
                .getStatusCodeValue();

        assertThat(statusCode).isEqualTo(200);

        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.assertIsSatisfied();
    }
}