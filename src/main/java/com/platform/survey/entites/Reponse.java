package com.platform.survey.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Utilisateur utilisateur;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Question question;

    private String valeur;

    private LocalDateTime dateReponse;
}