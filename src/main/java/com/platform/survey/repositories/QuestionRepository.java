package com.platform.survey.repositories;

import com.platform.survey.entites.Question;
import com.platform.survey.enums.TypeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySondageId(Long sondageId);

    List<Question> findByType(TypeQuestion type);

    Long countBySondageId(Long sondageId);
}