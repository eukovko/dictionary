package com.kovko.dictionary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Author: eukovko
 * Date: 6/15/2020
 */
@Entity
@Getter
@Setter
public class MiniCardEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Integer sourceLanguage;
    private Integer targetLanguage;
    private String heading;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "translation_id")
    private TranslationEntity translation;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> seeAlso;

}
