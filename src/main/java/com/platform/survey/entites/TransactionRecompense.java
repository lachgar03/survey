package com.platform.survey.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class TransactionRecompense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Utilisateur utilisateur;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Recompense recompense;

    private LocalDate dateTransaction;
}