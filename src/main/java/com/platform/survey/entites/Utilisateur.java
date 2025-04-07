package com.platform.survey.entites;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Profil profil;

    private int xp;
    private int niveau;
    @ManyToMany
    private List<Badge> badges;

    @OneToMany(mappedBy = "createur", fetch = FetchType.LAZY)
    private List<Sondage> sondagesCrees;

    @OneToMany(mappedBy = "utilisateur")
    private List<Reponse> reponses;

    @OneToMany(mappedBy = "utilisateur")
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
