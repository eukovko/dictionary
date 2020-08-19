package com.kovko.dictionary.route;

import com.kovko.dictionary.processor.FileProcessor;
import com.kovko.dictionary.processor.HttpOperationFailedProcessor;
import com.kovko.dictionary.processor.MinicardTranslationProcessor;
import org.springframework.stereotype.Component;

/**
 * Author: eukovko
 * Date: 6/20/2020
 */
@Component
public class FileRoute extends BaseRoute {

    private final FileProcessor fileProcessor;
    private final MinicardTranslationProcessor minicardTranslationProcessor;

    public FileRoute(HttpOperationFailedProcessor httpOperationFailedProcessor, FileProcessor fileProcessor,
                     MinicardTranslationProcessor minicardTranslationProcessor) {
        super(httpOperationFailedProcessor);
        this.fileProcessor = fileProcessor;
        this.minicardTranslationProcessor = minicardTranslationProcessor;
    }

    @Override
    public void configure() {

        // TODO: 5/15/2020 Add processor via wireTap and move sourceFile (IOException?)
        from("file:dictionary-camel/input?noop=true")
                .streamCaching()
                .log("FILENAME ${header.CamelFileName}")
                .log("FILE BODY: ${body}")
                .process(fileProcessor)
                .split(body())
                .log("${body}")
                .process(minicardTranslationProcessor)
                .to("{{api.translation.minicard.input}}");

    }
}
