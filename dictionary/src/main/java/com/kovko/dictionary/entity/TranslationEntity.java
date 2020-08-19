package com.kovko.dictionary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: eukovko
 * Date: 6/16/2020
 */
@Entity
@Getter
@Setter
public class TranslationEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String heading;
    private String translation;
    private String dictionaryName;
    private String soundName;
    private Integer type;
    private String originalWord;
}
