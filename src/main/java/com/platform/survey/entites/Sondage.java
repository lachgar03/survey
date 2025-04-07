package com.platform.survey.entites;

import com.platform.survey.enums.EtatSondage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sondage {
    @Id @GeneratedValue
    private Long id;
    private String description;
    private LocalDate dateCreation;
    @Enumerated(EnumType.STRING)
    private EtatSondage statut;

    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur createur;

    @OneToMany(mappedBy = "sondage", cascade = CascadeType.ALL)
    private List<Question> questions;

    public void ajouterQuestion(Question question) {
        questions.add(question);
        question.setSondage(this);
    }
    public void definirCreateur(Utilisateur createur) {
        this.createur = createur;
        createur.getSondagesCrees().add(this);
    }


}

