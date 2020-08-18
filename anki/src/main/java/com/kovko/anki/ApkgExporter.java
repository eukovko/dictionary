package com.kovko.anki;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kovko.anki.json.Media;
import com.kovko.anki.serializer.*;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Author: eukovko
 * Date: 5/31/2020
 */
public class ApkgExporter implements Closeable {

    private static final String COLLECTION_INSERT =
            "INSERT INTO col (id, crt, mod, scm, ver, dty, usn, ls, conf, models, decks, dconf, tags) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String NOTE_INSERT =
            "INSERT INTO notes (id, guid, mid, mod, usn, tags, flds, sfld, csum, flags, data) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CARD_INSERT =
            "INSERT INTO cards (id, nid, did, ord, mod, usn, type, queue, due, ivl, " +
                    "factor, reps, lapses, left, odue, odid, flags, data) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQLITE = "jdbc:sqlite:";
    private static final String SQL_STATEMENT_SEPARATOR = ";";
    private static final String SCHEMA = "schema.sql";

    private static final String DB_PATH = "C:/tutorials/anki-dictionary/dictionary-camel/anki/apkg/";
    private static final String DB_FILE = DB_PATH + "collection.anki2";

    private static final int NOTE_ID_INDEX = 1;
    private static final int NOTE_GUID_INDEX = 2;
    private static final int NOTE_MODEL_INDEX = 3;
    private static final int NOTE_LAST_MODIFIED_INDEX = 4;
    private static final int NOTE_USN_INDEX = 5;
    private static final int NOTE_TAGS_INDEX = 6;
    private static final int NOTE_FIELDS_INDEX = 7;
    private static final int NOTE_SORT_FIELD_INDEX = 8;
    private static final int NOTE_CHECK_SUM_INDEX = 9;
    private static final int NOTE_FLAGS_INDEX = 10;
    private static final int NOTE_DATA_INDEX = 11;
    private static final int CARD_ID_INDEX = 1;
    private static final int CARD_NODE_ID_INDEX = 2;
    private static final int CARD_DECK_ID_INDEX = 3;
    private static final int CARD_ORDINAL_INDEX = 4;
    private static final int CARD_MODIFICATION_TIME_INDEX = 5;
    private static final int CARD_USN_INDEX = 6;
    private static final int CARD_TYPE_INDEX = 7;
    private static final int CARD_QUEUE_INDEX = 8;
    private static final int CARD_DUE_INDEX = 9;
    private static final int CARD_INTERVAL_INDEX = 10;
    private static final int CARD_EASE_FACTOR_INDEX = 11;
    private static final int COLLECTION_DECK_CONFIGURATION_INDEX = 12;
    private static final int CARD_NUMBER_OF_REVIEWS_INDEX = 12;
    private static final int COLLECTION_TAGS_INDEX = 13;
    private static final int CARD_NUMBER_OF_LAPSES_INDEX = 13;
    private static final int CARD_LEFT_INDEX = 14;
    private static final int CARD_ORIGINAL_DUE_INDEX = 15;
    private static final int CARD_ORIGINAL_DECK_ID_INDEX = 16;
    private static final int CARD_FLAGS_INDEX = 17;
    private static final int CARD_DATA_INDEX = 18;
    private static final int COLLECTION_ID_INDEX = 1;
    private static final int COLLECTION_CREATION_DATE_INDEX = 2;
    private static final int COLLECTION_LAST_MODIFIED_INDEX = 3;
    private static final int COLLECTION_SCHEMA_LAST_MODIFIED_INDEX = 4;
    private static final int COLLECTION_VERSION_INDEX = 5;
    private static final int COLLECTION_DIRTY_INDEX = 6;
    private static final int COLLECTION_USN_INDEX = 7;
    private static final int COLLECTION_LAST_SYNC_TIME_INDEX = 8;
    private static final int COLLECTION_CONFIGURATION_INDEX = 9;
    private static final int COLLECTION_MODELS_INDEX = 10;
    private static final int COLLECTION_DECKS_INDEX = 11;

    private Connection connection;
    private ObjectMapper objectMapper;
    private List<Path> cleanUp = new ArrayList<>();

    public ApkgExporter() {
        initConnection();
        initObjectMapper();
    }

    private void initConnection() {
        try {
            connection = DriverManager.getConnection(SQLITE + DB_FILE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initObjectMapper() {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new CollectionConfigurationSerializer());
        module.addSerializer(new DeckConfigurationSerializer());
        module.addSerializer(new DeckConfigurationsSerializer());
        module.addSerializer(new DeckSerializer());
        module.addSerializer(new DecksSerializer());
        module.addSerializer(new ModelSerializer());
        module.addSerializer(new ModelsSerializer());
        module.addSerializer(new FieldSerializer());
        module.addSerializer(new TemplateSerializer());
        module.addSerializer(new LapsedCardsConfigurationSerializer());
        module.addSerializer(new ReviewCardsConfigurationSerializer());
        module.addSerializer(new NewCardsConfigurationSerializer());
        module.addSerializer(new MediaFilesSerializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    public void initDataBase() throws SQLException, IOException {

        DriverManager.getDriver(SQLITE);
        try (Statement statement = connection.createStatement()) {
            byte[] sqlSchema =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(SCHEMA).readAllBytes();
            String schema = new String(sqlSchema, Charset.defaultCharset());
            for (String s : schema.split(SQL_STATEMENT_SEPARATOR)) {
                statement.execute(s);
            }
        }
    }

    public void exportApkg(String filename, Media.MediaFiles mediaFiles) throws IOException {

        Path collection = Paths.get(DB_PATH, "collection.anki2");
        List<Path> media = exportMedia(mediaFiles);

        ArrayList<Path> pack = new ArrayList<>();
        pack.add(collection);
        pack.addAll(media);
        cleanUp.addAll(pack);

        try (FileOutputStream fos = new FileOutputStream(DB_PATH + "/" + filename + ".apkg");
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (Path path : pack) {

                ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                zipOut.putNextEntry(zipEntry);

                zipOut.write(Files.readAllBytes(path));
            }
        }

    }

    public List<Path> exportMedia(Media.MediaFiles mediaFiles) {

        List<Path> result = new ArrayList<>();
        Path media = Paths.get(DB_PATH, "media");
        String mediaFolder = "C:/tutorials/anki-dictionary/dictionary-camel/lingvo";
        List<Media> files = mediaFiles.getMediaFiles();
        try {
            for (int i = 0; i < files.size(); i++) {
                Path originalMediaFile = Paths.get(mediaFolder, files.get(i).getFileName());
                Path copyMediaFile = Paths.get(DB_PATH, String.valueOf(i));
                Files.copy(originalMediaFile, copyMediaFile, StandardCopyOption.REPLACE_EXISTING);
                result.add(copyMediaFile);
            }
            Files.writeString(media, objectMapper.writeValueAsString(mediaFiles));
            result.add(media);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void saveNote(Note note) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(NOTE_INSERT)) {

            preparedStatement.setLong(NOTE_ID_INDEX, note.getId());
            preparedStatement.setString(NOTE_GUID_INDEX, note.getGloballyUniqueId());
            preparedStatement.setLong(NOTE_MODEL_INDEX, note.getModel().getId());
            preparedStatement.setLong(NOTE_LAST_MODIFIED_INDEX, note.getLastModified());
            preparedStatement.setInt(NOTE_USN_INDEX, note.getUpdateSequenceNumber());
            preparedStatement.setString(NOTE_TAGS_INDEX, String.join(" ", note.getTags()));
            preparedStatement.setString(NOTE_FIELDS_INDEX, note.getFields());
            preparedStatement.setString(NOTE_SORT_FIELD_INDEX, note.getSortField());
            preparedStatement.setLong(NOTE_CHECK_SUM_INDEX, note.getCheckSum());
            preparedStatement.setInt(NOTE_FLAGS_INDEX, note.getFlags());
            preparedStatement.setString(NOTE_DATA_INDEX, note.getData());

            preparedStatement.execute();
        }
    }

    public void saveCard(Card card) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(CARD_INSERT)) {

            preparedStatement.setLong(CARD_ID_INDEX, card.getId());
            preparedStatement.setLong(CARD_NODE_ID_INDEX, card.getNote().getId());
            preparedStatement.setLong(CARD_DECK_ID_INDEX, card.getDeck().getId());
            preparedStatement.setInt(CARD_ORDINAL_INDEX, card.getOrdinal());
            preparedStatement.setLong(CARD_MODIFICATION_TIME_INDEX, card.getModificationTime());
            preparedStatement.setLong(CARD_USN_INDEX, card.getUpdateSequenceNumber());
            preparedStatement.setInt(CARD_TYPE_INDEX, card.getType().getTypeCode());
            preparedStatement.setLong(CARD_QUEUE_INDEX, card.getQueue());
            preparedStatement.setLong(CARD_DUE_INDEX, card.getDue());
            preparedStatement.setLong(CARD_INTERVAL_INDEX, card.getInterval());
            preparedStatement.setLong(CARD_EASE_FACTOR_INDEX, card.getEaseFactor());
            preparedStatement.setLong(CARD_NUMBER_OF_REVIEWS_INDEX, card.getNumberOfReviews());
            preparedStatement.setLong(CARD_NUMBER_OF_LAPSES_INDEX, card.getNumberOfLapses());
            preparedStatement.setLong(CARD_LEFT_INDEX, card.getLeft());
            preparedStatement.setLong(CARD_ORIGINAL_DUE_INDEX, card.getOriginalDue());
            preparedStatement.setLong(CARD_ORIGINAL_DECK_ID_INDEX, card.getOriginalDeckId());
            preparedStatement.setLong(CARD_FLAGS_INDEX, card.getFlags());
            preparedStatement.setString(CARD_DATA_INDEX, card.getData());

            preparedStatement.execute();
        }
    }

    public void saveCollection(Collection collection) throws SQLException, JsonProcessingException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(COLLECTION_INSERT)) {

            preparedStatement.setLong(COLLECTION_ID_INDEX, collection.getId());
            preparedStatement.setLong(COLLECTION_CREATION_DATE_INDEX, collection.getCreationDate());
            preparedStatement.setLong(COLLECTION_LAST_MODIFIED_INDEX, collection.getLastModified());
            preparedStatement.setLong(COLLECTION_SCHEMA_LAST_MODIFIED_INDEX, collection.getSchemaLastModified());
            preparedStatement.setLong(COLLECTION_VERSION_INDEX, collection.getVersion());
            preparedStatement.setInt(COLLECTION_DIRTY_INDEX, collection.getDirty());
            preparedStatement.setInt(COLLECTION_USN_INDEX, collection.getUpdateSequenceNumber());
            preparedStatement.setLong(COLLECTION_LAST_SYNC_TIME_INDEX, collection.getLastSyncTime());

            preparedStatement.setString(COLLECTION_CONFIGURATION_INDEX,
                    objectMapper.writeValueAsString(collection.getConfiguration()));
            preparedStatement.setString(COLLECTION_MODELS_INDEX,
                    objectMapper.writeValueAsString(collection.getModels()));
            preparedStatement.setString(COLLECTION_DECKS_INDEX, objectMapper.writeValueAsString(collection.getDecks()));
            preparedStatement
                    .setString(COLLECTION_DECK_CONFIGURATION_INDEX,
                            objectMapper.writeValueAsString(collection.getDeckConfiguration()));
            preparedStatement.setString(COLLECTION_TAGS_INDEX, objectMapper.writeValueAsString(collection.getTags()));
            preparedStatement.execute();
        }

    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Path path : cleanUp) {
            Files.delete(path);
        }
    }
}
