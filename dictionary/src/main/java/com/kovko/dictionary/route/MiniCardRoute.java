package com.kovko.dictionary.route;

import com.kovko.dictionary.dto.MiniCard;
import com.kovko.dictionary.dto.TranslationBatch;
import com.kovko.dictionary.mapper.Mapper;
import com.kovko.dictionary.processor.*;
import com.kovko.dictionary.repository.MinicardRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/14/2020
 */
@Component
@Slf4j
public class MiniCardRoute extends BaseRoute {

    private static final int PORT = 9090;
    private final DataFormat minicardJacksonDataFormat = new JacksonDataFormat(MiniCard.class);
    private final DataFormat translationBatchJacksonDataFormat = new JacksonDataFormat(TranslationBatch.class);
    private final AuthenticationProcessor authenticationProcessor;
    private final BatchRestProcessor batchRestProcessor;
    private final MinicardTranslationProcessor minicardTranslationProcessor;
    private final Mapper mapper;
    private final MiniCardDatabaseProcessor miniCardDatabaseProcessor;
    private final MinicardRepository minicardRepository;


    public MiniCardRoute(HttpOperationFailedProcessor httpOperationFailedProcessor,
                         AuthenticationProcessor authenticationProcessor, BatchRestProcessor batchRestProcessor,
                         MinicardTranslationProcessor minicardTranslationProcessor, Mapper mapper,
                         MiniCardDatabaseProcessor miniCardDatabaseProcessor, MinicardRepository minicardRepository) {
        super(httpOperationFailedProcessor);
        this.authenticationProcessor = authenticationProcessor;
        this.batchRestProcessor = batchRestProcessor;
        this.minicardTranslationProcessor = minicardTranslationProcessor;
        this.mapper = mapper;
        this.miniCardDatabaseProcessor = miniCardDatabaseProcessor;
        this.minicardRepository = minicardRepository;
    }

    @Override
    public void configure() throws Exception {

        super.configure();

        restConfiguration().host("localhost").component("jetty").port(PORT);

        rest().get("/minicard")
                .id("minicard")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:setTextHeader");

        from("direct:setTextHeader")
                .setBody(simple("${header.text}"))
                .to("{{api.translation.minicard.input}}");

        rest().post("/minicard/batch")
                .id("minicard.batch")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:minicard.batch");

        from("direct:minicard.batch")
                .unmarshal(translationBatchJacksonDataFormat)
                .to("direct:minicard.translationBatch");

        from("direct:minicard.importDeck")
                .setBody(simple("${body.translationBatch}"))
                .to("direct:minicard.translationBatch");

        from("direct:minicard.translationBatch")
                .streamCaching()
                .process(batchRestProcessor)
                .split(body())
                .log("Minicard translation batch: ${body}")
                .process(minicardTranslationProcessor)
                .to("{{api.translation.minicard.input}}");

        from("{{api.translation.minicard.input}}")
                .routeId("api.translation.minicard.input")
                .to("direct:minicard");

        from("direct:minicard")
                .streamCaching()
                .process(minicardTranslationProcessor)
                .process(miniCardDatabaseProcessor)
                .choice()
                .when(header("translationExists").isEqualTo(false))
                    .setHeader(HttpHeaders.AUTHORIZATION, authenticationProcessor::getAuthorizationHeader)
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .to("{{api.translation.minicard.endpoint}}")
                    .unmarshal(minicardJacksonDataFormat)
                    .bean(mapper, "toMiniCardEntity")
                    .bean(minicardRepository, "save")
                .end()
                .bean(mapper, "toMiniCard")
                .setHeader("soundName", simple("${body.translation.soundName}"))
                .setHeader("dictionaryName", simple("${body.translation.dictionaryName}"))
                .enrich("direct:sound", (oldExchange, newExchange) -> oldExchange)
                .marshal(minicardJacksonDataFormat)
                .transform(body());
    }

}
