package com.platform.survey.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reponse {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Question question;

    private String valeur;

    private LocalDateTime dateReponse;
}