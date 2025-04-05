package com.platform.survey.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Option {
    @Id
    @GeneratedValue
    private Long id;
    private String texte;

    @ManyToOne
    private Question question;
}
