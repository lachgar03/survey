package com.platform.survey.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Recompense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private int coutXp;

    @OneToMany(mappedBy = "recompense", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TransactionRecompense> transactions;
}
