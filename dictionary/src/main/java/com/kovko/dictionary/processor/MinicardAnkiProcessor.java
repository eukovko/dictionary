package com.kovko.dictionary.processor;

import com.kovko.anki.Note;
import com.kovko.anki.json.Media;
import com.kovko.dictionary.entity.MiniCardEntity;
import com.kovko.dictionary.repository.MinicardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: eukovko
 * Date: 7/18/2020
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MinicardAnkiProcessor extends AbstractAnkiProcessor {

    private final MinicardRepository minicardRepository;

    @Override
    protected List<Note> createNotes(List<Media> files, List<String> words) {

        List<MiniCardEntity> miniCards = words.stream().map(minicardRepository::findByHeading)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return miniCards.stream().map((MiniCardEntity entity) -> {
            String heading = entity.getHeading();
            String translation = entity.getTranslation().getTranslation();
            String soundName = entity.getTranslation().getSoundName();
            if (!soundName.isBlank()) {
                files.add(new Media(soundName));
            }
            return new Note(heading, String.format(AUDIO_FIELD_FORMAT, soundName), translation);
        }).collect(Collectors.toList());
    }
}
