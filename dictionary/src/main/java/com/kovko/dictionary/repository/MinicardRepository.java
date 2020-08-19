package com.kovko.dictionary.repository;

import com.kovko.dictionary.entity.MiniCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: eukovko
 * Date: 4/19/2020
 */
public interface MinicardRepository extends JpaRepository<MiniCardEntity, String> {

    Optional<MiniCardEntity> findByHeadingAndSourceLanguageAndTargetLanguage(
            String heading, Integer sourceLanguage, Integer targetLanguage);

    Optional<MiniCardEntity> findByHeading(String heading);

}
