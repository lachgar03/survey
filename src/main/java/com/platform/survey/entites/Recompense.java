package com.platform.survey.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Recompense {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String description;
    private int coutXp;
}
