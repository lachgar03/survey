package com.platform.survey.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String metier;
    private String region;
    private int age;
    private String numeroTelephone;

    @OneToOne(mappedBy = "profil", cascade = CascadeType.ALL, orphanRemoval = true)
    private Utilisateur utilisateur;

    @ElementCollection
    private List<String> interets;
}

