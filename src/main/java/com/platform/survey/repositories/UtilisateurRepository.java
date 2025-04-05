package com.platform.survey.repositories;

import com.platform.survey.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email); // Critique pour l'authentification

    // Pour le leaderboard
    List<Utilisateur> findTop10ByOrderByXpDesc();
}
