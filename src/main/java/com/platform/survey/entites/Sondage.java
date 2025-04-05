package com.platform.survey.entites;

import com.platform.survey.enums.EtatSondage;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
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


}

