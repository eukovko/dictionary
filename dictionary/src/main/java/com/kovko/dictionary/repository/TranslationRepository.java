package com.kovko.dictionary.repository;

import com.kovko.dictionary.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: eukovko
 * Date: 4/19/2020
 */
public interface TranslationRepository extends JpaRepository<TranslationEntity, String> {
}
