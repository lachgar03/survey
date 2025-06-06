package com.platform.survey.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texte;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Question question;

}
