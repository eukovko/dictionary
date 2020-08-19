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
@Getter
@Setter
public class ExampleEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String example;
    private String translation;

    @Override
    public String toString(){
        return String.format("%s %s", example, translation);
    }
}
