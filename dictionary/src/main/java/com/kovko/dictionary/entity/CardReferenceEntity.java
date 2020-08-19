package com.kovko.dictionary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Entity
@Setter
@Getter
public class CardReferenceEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private String dictionary;
    private String articleId;
}
