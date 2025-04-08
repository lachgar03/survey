package com.platform.survey.repositories;

import com.platform.survey.entites.Utilisateur;
import com.platform.survey.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRole(Role role);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.xp = u.xp + :points WHERE u.id = :userId")
    void incrementXp(@Param("userId") Long userId, @Param("points") Integer points);

    List<Utilisateur> findTop10ByOrderByXpDesc();
}