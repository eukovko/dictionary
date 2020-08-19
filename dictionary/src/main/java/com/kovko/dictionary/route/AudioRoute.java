package com.kovko.dictionary.route;

import com.kovko.dictionary.processor.AuthenticationProcessor;
import com.kovko.dictionary.processor.Base64Processor;
import com.kovko.dictionary.processor.HttpOperationFailedProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: eukovko
 * Date: 6/20/2020
 */
@Component
@Slf4j
public class AudioRoute extends BaseRoute {

    private final AuthenticationProcessor authenticationProcessor;
    private final Base64Processor base64Processor;

    public AudioRoute(HttpOperationFailedProcessor httpOperationFailedProcessor,
                      AuthenticationProcessor authenticationProcessor, Base64Processor base64Processor) {
        super(httpOperationFailedProcessor);
        this.authenticationProcessor = authenticationProcessor;
        this.base64Processor = base64Processor;
    }

    @Override
    public void configure() throws Exception {

        super.configure();

        // TODO: 5/2/2020 Add check if the file is exists and add overwrite header (not so important)
        from("direct:sound")
                .log("Audio route")
                .process(exchange -> {
                    String soundName = exchange.getIn().getHeader("soundName", String.class);
                    if (Files.exists(Paths.get("C:/tutorials/anki-dictionary/dictionary-camel/lingvo", soundName))) {
                        log.info("Audio file exists");
                        exchange.getIn().setHeader("audioFileExists", true);
                    } else {
                        log.info("Audio file doesn't exists");
                        exchange.getIn().setHeader("audioFileExists", false);
                    }
                })
                .choice()
                .when(header("audioFileExists").isEqualTo(false))
                    .setHeader(HttpHeaders.AUTHORIZATION, authenticationProcessor::getAuthorizationHeader)
                    .setHeader(Exchange.HTTP_QUERY,
                            simple("dictionaryName=${header.dictionaryName}&fileName=${header.soundName}"))
//                    .setHeader("fileName", simple("${header.soundName}"))
//                    .setHeader("dictionaryName", simple("${body.translation.dictionaryName}"))
                    .to("{{api.translation.audio.endpoint}}")
                    .process(base64Processor)
                    .to("file:dictionary-camel/lingvo?fileName=${header.soundName}")
                .end();

    }
}