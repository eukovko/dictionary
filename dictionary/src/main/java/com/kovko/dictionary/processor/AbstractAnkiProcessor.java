package com.kovko.dictionary.processor;

import com.kovko.anki.ApkgExporter;
import com.kovko.anki.Card;
import com.kovko.anki.Collection;
import com.kovko.anki.Note;
import com.kovko.anki.json.*;
import com.kovko.dictionary.dto.ImportDeck;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 7/18/2020
 */
@Slf4j
public abstract class AbstractAnkiProcessor implements Processor {

    protected static final String AUDIO_FIELD_FORMAT = "[sound:%s]";
    private static final String DB_PATH = "C:/tutorials/anki-dictionary/dictionary-camel/anki/apkg/";

    protected static Collection initCollection(String deckName) {

        Collection collection = new Collection();
        Deck deck = new Deck(deckName);

        Model model = new Model("Basic and Reverse");
        model.setDefaultDeck(deck);

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration();
        collectionConfiguration.setCurrentDeck(deck);
        collectionConfiguration.setActiveDecks(new ArrayList<>(List.of(deck)));
        collectionConfiguration.setCurrentModel(model);

        collection.setConfiguration(collectionConfiguration);
        collection.setModels(new Model.Models(new ArrayList<>(List.of(model))));
        collection.setDecks(new Deck.Decks(new ArrayList<>(List.of(deck))));
        collection.setDeckConfiguration(
                new DeckConfiguration.DeckConfigurations(new ArrayList<>(List.of(deck.getConfiguration()))));

        return collection;

    }

    public void process(Exchange exchange) throws Exception {

        ImportDeck importDeck = exchange.getIn().getBody(ImportDeck.class);
        List<String> words = importDeck.getTranslationBatch().getWords();

        log.info("Deck for import {}", importDeck);

        try (ApkgExporter apkgExporter = new ApkgExporter()) {

            apkgExporter.initDataBase();

            Collection collection = initCollection(importDeck.getDeckName());
            apkgExporter.saveCollection(collection);

            List<Media> files = new ArrayList<>();
            List<Note> notes = createNotes(files, words);

            for (Note note : notes) {
                note.setId(Instant.now().toEpochMilli());
                note.setModel(collection.getConfiguration().getCurrentModel());

                List<Card> cards = note.generateCards();

                apkgExporter.saveNote(note);
                for (Card card : cards) {
                    apkgExporter.saveCard(card);
                }
            }

            apkgExporter.exportApkg(importDeck.getPackageName(), new Media.MediaFiles(files));

            String dbPath = DB_PATH;
            AttachmentMessage message = exchange.getIn(AttachmentMessage.class);
            message.addAttachment(importDeck.getPackageName(),
                    new DataHandler(new FileDataSource(new File(dbPath + importDeck.getPackageName() + ".apkg"))));
        }
    }

    protected abstract List<Note> createNotes(List<Media> files, List<String> words);
}
