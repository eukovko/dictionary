package com.kovko.dictionary.repository;

import com.kovko.dictionary.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByTitle(String title);

    Optional<ArticleEntity> findByTitleAndDictionary(String title, String dictionaryName);
}
