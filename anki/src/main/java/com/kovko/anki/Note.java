package com.kovko.anki;

import com.kovko.anki.json.Deck;
import com.kovko.anki.json.Model;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Note {

    private static final String DELIMITER = String.valueOf((char) 31);

    /**
     * Creation time in epoch milliseconds
     */
    private long id = Instant.now().toEpochMilli();
    /**
     * GUID which is used for syncing
     */
    private String globallyUniqueId = UUID.randomUUID().toString();
    /**
     * Model for this note
     */
    private Model model;
    /**
     * Modification time in epoch seconds
     */
    private long lastModified = Instant.now().getEpochSecond();
    /**
     * Defines if the note has changes and need to be updated
     */
    private int updateSequenceNumber = -1;
    /**
     * Space separated list of tags
     */
    private List<String> tags = new ArrayList<>();
    /**
     * Concatenated fields separated by 0x1f (31) character
     */
    private String fields;
    /**
     * Field which is used for quick sorting and duplicate check
     * Usually the first field
     */
    private String sortField;
    /**
     * First 8 digits of sha1 hash of the first field
     */
    private long checkSum;
    /**
     * Currently unused field
     */
    private int flags = 0;
    /**
     * Currently unused field
     */
    private String data = "";

    public Note(String ... fields) {

        if (fields == null || fields.length == 0) {

            throw new IllegalArgumentException("Fields should not be null or empty");
        }
        // TODO: 5/17/2020 Add check for delimiter character in fields


        this.fields = String.join(DELIMITER, fields);
        this.sortField = fields[0];
        this.checkSum = calculateCheckSum(fields[0]);
    }

    private static long calculateCheckSum(String string){

        String hex = DigestUtils.sha1Hex(string);
        return Long.parseLong(hex.substring(0,8), 16);
    }

    public List<Card> generateCards() {

        return generateCards(this.model.getDefaultDeck());
    }

    public List<Card> generateCards(Deck deck) {


        // TODO: 6/21/2020 Increment Id manually
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Card cardReverse = new Card(this, 1, deck);
        Card cardDirect = new Card(this, 0, deck);
        return List.of(cardDirect,cardReverse);
    }



}
