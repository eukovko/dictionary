package com.kovko.dictionary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: eukovko
 * Date: 7/3/2020
 */
@Entity
@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String soundName;
    private String dictionary;
    private String articleId;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<MeaningEntity> meanings = new ArrayList<>();

}
