package com.kovko.dictionary.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Entity
@Setter
@Getter
public class MeaningEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<ExampleEntity> examples = new ArrayList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<CardReferenceEntity> cardReferenceEntities = new ArrayList<>();
}
