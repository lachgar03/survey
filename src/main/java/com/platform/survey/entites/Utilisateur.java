package com.platform.survey.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.platform.survey.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "profil_id") // specify the foreign key
    private Profil profil;

    public void setProfil(Profil profil) {
        this.profil = profil;
        if (profil != null) {
            profil.setUtilisateur(this);
        }
    }


    private int xp;
    private int niveau;
    @ManyToMany
    @JsonIgnore
    private List<Badge> badges;

    @OneToMany(mappedBy = "createur", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Sondage> sondagesCrees;

    @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference
    private List<Reponse> reponses;

    @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference
    private List<TransactionRecompense> transactionsRecompenses;

    public void ajouterBadge(Badge badge) {
        badges.add(badge);
        badge.getUtilisateurs().add(this);
    }

    public void ajouterSondageCree(Sondage sondage) {
        sondagesCrees.add(sondage);
        sondage.setCreateur(this);
    }

    public void ajouterReponse(Reponse reponse) {
        reponses.add(reponse);
        reponse.setUtilisateur(this);
    }

    public void ajouterTransactionRecompense(TransactionRecompense transaction) {
        transactionsRecompenses.add(transaction);
        transaction.setUtilisateur(this);
    }
}
