package com.platform.survey.repositories;

import com.platform.survey.entites.Sondage;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.enums.EtatSondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SondageRepository extends JpaRepository<Sondage, Long> {
    List<Sondage> findByStatut(EtatSondage statut);

    List<Sondage> findByCreateur(Utilisateur createur);

    List<Sondage> findByDescriptionContainingIgnoreCase(String motCle);

    List<Sondage> findByDateCreationAfter(LocalDate date);

    @Query("SELECT DISTINCT s FROM Sondage s WHERE s.id NOT IN " +
            "(SELECT DISTINCT q.sondage.id FROM Reponse r JOIN r.question q WHERE r.utilisateur.id = :userId) " +
            "AND s.statut = 'ACTIF'")
    List<Sondage> findSondagesNotAnsweredByUser(@Param("userId") Long userId);
}