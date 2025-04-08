package com.platform.survey.repositories;

import com.platform.survey.entites.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestionId(Long questionId);

    @Query("SELECT o, COUNT(r) as count FROM Option o JOIN Question q ON o.question.id = q.id " +
            "JOIN Reponse r ON r.question.id = q.id AND r.valeur = CAST(o.id AS string) " +
            "WHERE q.id = :questionId GROUP BY o.id ORDER BY count DESC")
    List<Object[]> findMostSelectedOptionsForQuestion(@Param("questionId") Long questionId);
}