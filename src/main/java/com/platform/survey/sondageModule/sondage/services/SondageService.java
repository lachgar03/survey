package com.platform.survey.sondageModule.sondage.services;

import com.platform.survey.entites.Question;
import com.platform.survey.entites.Sondage;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.enums.EtatSondage;
import com.platform.survey.enums.Role;
import com.platform.survey.repositories.QuestionRepository;
import com.platform.survey.repositories.SondageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SondageService {

    private final SondageRepository sondageRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public SondageService(SondageRepository sondageRepository, QuestionRepository questionRepository) {
        this.sondageRepository = sondageRepository;
        this.questionRepository = questionRepository;
    }

    public List<Sondage> listerSondagesActifs() {
        return sondageRepository.findByStatut(EtatSondage.ACTIF);
    }

    public List<Sondage> listerSondagesParStatut(EtatSondage statut) {
        return sondageRepository.findByStatut(statut);
    }

    public List<Sondage> listerSondagesParCreateur(Utilisateur createur) {
        return sondageRepository.findByCreateur(createur);
    }

    public List<Sondage> listerSondagesNonRepondus(Long userId) {
        return sondageRepository.findSondagesNotAnsweredByUser(userId);
    }

    public List<Sondage> rechercherSondages(String motCle) {
        return sondageRepository.findByDescriptionContainingIgnoreCase(motCle);
    }

    public Optional<Sondage> recupererSondage(Long sondageId) {
        return sondageRepository.findById(sondageId);
    }

    public Optional<Sondage> recupererSondageAvecQuestions(Long sondageId) {
        Optional<Sondage> sondageOpt = sondageRepository.findById(sondageId);
        if (sondageOpt.isPresent()) {
            Sondage sondage = sondageOpt.get();
            // Forcer le chargement des questions
            sondage.getQuestions().size();
            return Optional.of(sondage);
        }
        return Optional.empty();
    }


    public List<Question> recupererQuestionsDuSondage(Long sondageId) {
        return questionRepository.findBySondageId(sondageId);
    }


    public Long compterQuestionsDuSondage(Long sondageId) {
        return questionRepository.countBySondageId(sondageId);
    }


    @Transactional
    public Sondage creerSondage(Sondage sondage, Utilisateur createur) {
        if (createur.getRole() != Role.SONDEUR && createur.getRole() != Role.ADMIN) {
            throw new RuntimeException("Seuls les utilisateurs avec le rôle SONDEUR peuvent créer des sondages");
        }

        sondage.setCreateur(createur);
        sondage.setDateCreation(LocalDate.now());
        if (sondage.getStatut() == null) {
            sondage.setStatut(EtatSondage.ACTIF);
        }

        return sondageRepository.save(sondage);
    }


    @Transactional
    public Sondage mettreAJourSondage(Long sondageId, Sondage sondageMaj, Utilisateur utilisateur) {
        Optional<Sondage> sondageExistantOpt = sondageRepository.findById(sondageId);

        if (sondageExistantOpt.isEmpty()) {
            throw new IllegalArgumentException("Sondage non trouvé avec l'ID: " + sondageId);
        }

        Sondage sondageExistant = sondageExistantOpt.get();

        if (utilisateur.getRole() != Role.ADMIN && !sondageExistant.getCreateur().getId().equals(utilisateur.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier ce sondage");
        }


        sondageExistant.setDescription(sondageMaj.getDescription());

        return sondageRepository.save(sondageExistant);
    }


    @Transactional
    public boolean changerStatutSondage(Long sondageId, EtatSondage nouveauStatut, Utilisateur utilisateur) {
        Optional<Sondage> sondageOpt = sondageRepository.findById(sondageId);

        if (sondageOpt.isEmpty()) {
            return false;
        }

        Sondage sondage = sondageOpt.get();

        if (utilisateur.getRole() != Role.ADMIN && !sondage.getCreateur().getId().equals(utilisateur.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier ce sondage");
        }

        sondage.setStatut(nouveauStatut);
        sondageRepository.save(sondage);
        return true;
    }


    @Transactional
    public boolean supprimerSondage(Long sondageId, Utilisateur utilisateur) {
        Optional<Sondage> sondageOpt = sondageRepository.findById(sondageId);

        if (sondageOpt.isEmpty()) {
            return false;
        }

        Sondage sondage = sondageOpt.get();

        if (utilisateur.getRole() != Role.ADMIN && !sondage.getCreateur().getId().equals(utilisateur.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce sondage");
        }

        sondageRepository.delete(sondage);
        return true;
    }
}