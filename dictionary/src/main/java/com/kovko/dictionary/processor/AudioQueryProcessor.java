package com.kovko.dictionary.processor;

import com.kovko.dictionary.dto.MiniCard;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class AudioQueryProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getMessage();
        MiniCard miniCard = message.getBody(MiniCard.class);

        String dictionaryName = miniCard.getTranslation().getDictionaryName();
        String soundName = miniCard.getTranslation().getSoundName();

        message.setHeader("fileName", soundName);
        message.setHeader("dictionaryName", dictionaryName);

    }
}
