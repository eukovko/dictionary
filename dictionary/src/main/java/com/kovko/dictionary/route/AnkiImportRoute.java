package com.kovko.dictionary.route;

import com.kovko.dictionary.dto.ImportDeck;
import com.kovko.dictionary.processor.ArticleAnkiProcessor;
import com.kovko.dictionary.processor.HttpOperationFailedProcessor;
import com.kovko.dictionary.processor.MinicardAnkiProcessor;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/23/2020
 */
@Component
public class AnkiImportRoute extends BaseRoute {

    private static final int PORT = 9090;
    private final DataFormat jacksonDataFormat = new JacksonDataFormat(ImportDeck.class);
    private final ArticleAnkiProcessor articleAnkiProcessor;
    private final MinicardAnkiProcessor minicardAnkiProcessor;

    public AnkiImportRoute(HttpOperationFailedProcessor httpOperationFailedProcessor,
                           ArticleAnkiProcessor articleAnkiProcessor, MinicardAnkiProcessor minicardAnkiProcessor) {
        super(httpOperationFailedProcessor);
        this.articleAnkiProcessor = articleAnkiProcessor;
        this.minicardAnkiProcessor = minicardAnkiProcessor;
    }

    @Override
    public void configure() throws Exception {

        super.configure();

        restConfiguration().host("localhost").component("jetty").port(PORT);

        rest().post("/dictionary/minicard/import")
                .id("minicard.import")
                .to("direct:minicard/import");

        from("direct:minicard/import")
                .unmarshal(jacksonDataFormat)
                .enrich("direct:minicard.importDeck", (oldExchange, newExchange) -> oldExchange)
                .process(minicardAnkiProcessor);

        rest().post("/dictionary/article/import")
                .id("article.import")
                .to("direct:article/import");

        from("direct:article/import")
                .unmarshal(jacksonDataFormat)
                .enrich("direct:article.importDeck", (oldExchange, newExchange) -> oldExchange)
                .process(articleAnkiProcessor);

    }
}
