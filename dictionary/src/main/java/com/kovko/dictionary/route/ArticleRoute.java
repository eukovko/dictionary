package com.kovko.dictionary.route;

import com.kovko.dictionary.dto.ArticleApiPresentation;
import com.kovko.dictionary.dto.TranslationBatch;
import com.kovko.dictionary.mapper.Mapper;
import com.kovko.dictionary.processor.*;
import com.kovko.dictionary.repository.ArticleRepository;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/29/2020
 */
@Component
public class ArticleRoute extends BaseRoute{

    private static final int PORT = 9090;
    private final DataFormat articleJacksonDataFormat = new JacksonDataFormat(ArticleApiPresentation.class);
    private final DataFormat translationBatchJacksonDataFormat = new JacksonDataFormat(TranslationBatch.class);
    private final AuthenticationProcessor authenticationProcessor;
    private final ArticleTranslationProcessor articleTranslateProcessor;
    private final ArticleDatabaseProcessor articleDatabaseProcessor;
    private final BatchRestProcessor batchRestProcessor;
    private final Mapper mapper;
    private final ArticleRepository articleRepository;

    public ArticleRoute(HttpOperationFailedProcessor httpOperationFailedProcessor,
                        AuthenticationProcessor authenticationProcessor,
                        ArticleTranslationProcessor articleTranslateProcessor,
                        ArticleDatabaseProcessor articleDatabaseProcessor,
                        BatchRestProcessor batchRestProcessor,
                        Mapper mapper, ArticleRepository articleRepository) {
        super(httpOperationFailedProcessor);
        this.authenticationProcessor = authenticationProcessor;
        this.articleTranslateProcessor = articleTranslateProcessor;
        this.articleDatabaseProcessor = articleDatabaseProcessor;
        this.batchRestProcessor = batchRestProcessor;
        this.mapper = mapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public void configure() throws Exception {

        super.configure();

        restConfiguration().host("localhost").component("jetty").port(PORT);

        rest().get("/article")
                .id("article")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:setHeadingHeader");

        from("direct:setHeadingHeader")
                .setBody(simple("${header.heading}"))
                .to("{{api.translation.article.input}}");

        rest().post("/article/batch")
                .id("article.batch")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:article.batch");

        from("direct:article.batch")
                .unmarshal(translationBatchJacksonDataFormat)
                .to("direct:article.translationBatch");

        from("direct:article.importDeck")
                .setBody(simple("${body.translationBatch}"))
                .to("direct:article.translationBatch");

        from("direct:article.translationBatch")
                .process(batchRestProcessor)
                .split(body())
                .log("Batch article body ${body}")
                .process(articleTranslateProcessor)
                .to("{{api.translation.article.input}}");

        from("{{api.translation.article.input}}")
                .routeId("api.translation.article.input")
                .to("direct:article");

        from("direct:article")
                .streamCaching()
                .process(articleTranslateProcessor)
                .process(articleDatabaseProcessor)
                .choice()
                .when(header("translationExists").isEqualTo(false))
                    .setHeader(HttpHeaders.AUTHORIZATION, authenticationProcessor::getAuthorizationHeader)
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .to("{{api.translation.article.endpoint}}")
                    .unmarshal(articleJacksonDataFormat)
                    .bean(mapper, "toArticleEntity")
                    .bean(articleRepository, "save")
                .end()
                .bean(mapper, "toArticle")
                .setHeader("soundName", simple("${body.soundName}"))
                .setHeader("dictionaryName", simple("${body.dictionary}"))
                .enrich("direct:sound", (oldExchange, newExchange) -> oldExchange)
                .marshal(articleJacksonDataFormat)
                .transform(body());
    }
}
