package com.kovko.dictionary.processor;


import com.kovko.anki.Note;
import com.kovko.anki.json.Media;
import com.kovko.dictionary.entity.ArticleEntity;
import com.kovko.dictionary.entity.ExampleEntity;
import com.kovko.dictionary.entity.MeaningEntity;
import com.kovko.dictionary.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: eukovko
 * Date: 6/20/2020
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleAnkiProcessor extends AbstractAnkiProcessor {

    private final ArticleRepository articleRepository;

    @Override
    protected List<Note> createNotes(List<Media> files, List<String> words) {

        List<ArticleEntity> articles = words.stream().map(articleRepository::findByTitle)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return articles.stream().map((ArticleEntity entity) -> {
            String title = entity.getTitle();
            String meaning = "";
            for (MeaningEntity entityMeaning : entity.getMeanings()) {
                meaning = meaning + entityMeaning.getTitle() + "<br>";
                for (ExampleEntity example : entityMeaning.getExamples()) {
                    meaning = meaning + "<span>    </span>" + example + "<br>";
                }
            }
            String soundName = entity.getSoundName();
            if (!soundName.isBlank()) {
                files.add(new Media(soundName));
            }
            return new Note(title, String.format(AUDIO_FIELD_FORMAT, soundName), meaning);

        }).collect(Collectors.toList());
    }

}
