package com.platform.survey.repositories;

import com.platform.survey.entites.Reponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, Long> {
    List<Reponse> findByUtilisateurId(Long utilisateurId);

    @Query("SELECT r FROM Reponse r WHERE r.question.sondage.id = :sondageId")
    List<Reponse> findByQuestionSondageId(@Param("sondageId") Long sondageId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reponse r " +
            "WHERE r.utilisateur.id = :userId AND r.question.sondage.id = :sondageId")
    boolean existsByUtilisateurIdAndQuestionSondageId(@Param("userId") Long userId, @Param("sondageId") Long sondageId);

    List<Reponse> findByDateReponseBetween(LocalDateTime debut, LocalDateTime fin);

    List<Reponse> findByUtilisateurIdOrderByDateReponseDesc(Long utilisateurId, Pageable pageable);

    Long countByQuestionId(Long questionId);
}