package com.kovko.anki.json;

import com.kovko.anki.json.config.LapsedCardsConfiguration;
import com.kovko.anki.json.config.NewCardsConfiguration;
import com.kovko.anki.json.config.ReviewCardsConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: eukovko
 * Date: 5/17/2020
 */
@Getter
@Setter
// TODO: 5/17/2020 The same wrapper as for models
public class DeckConfiguration {

    /**
     * The name of the configuration
     */
    private String name;
    /**
     * The id of the configuration.
     * Automatically generated long.
     */
    private long id = 1;
    /**
     * The modification time of the configuration in epoch seconds
     */
    private long modificationTime = 0;
    /**
     * Defines if the configuration has changes and need to be updated
     */
    private long updateSequenceNumber = 0;

    /**
     * Max time in second after which timer will be switched off
     * Need to prevent incorrect statistics
     */
    private int timerTimeout = 60;
    /**
     * Defines if the audio should be automatically played
     */
    private boolean audioAutoplay = true;
    /**
     * Defines if the audio should be replayed on the answer card
     */
    private boolean audioAnswerReplay = true;
    /**
     * Defines whether the timer is shown
     */
    private boolean showTimer = false;

    /**
     * Configuration for lapsed cards
     */
    private LapsedCardsConfiguration lapsedCardsConfiguration = new LapsedCardsConfiguration();
    /**
     * Configuration for reviewed cards
     */
    private ReviewCardsConfiguration reviewCardsConfiguration = new ReviewCardsConfiguration();
    /**
     * Configuration for new cards
     */
    private NewCardsConfiguration newCardsConfiguration = new NewCardsConfiguration();

    public DeckConfiguration(String name) {
        this.name = name;
    }

    /**
     * Author: eukovko
     * Date: 5/31/2020
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class DeckConfigurations {

        private List<DeckConfiguration> deckConfigurations;
    }
}
