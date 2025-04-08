package com.platform.survey.sondageModule.reponse.services;

import com.platform.survey.entites.Question;
import com.platform.survey.entites.Reponse;
import com.platform.survey.entites.Sondage;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.enums.TypeQuestion;
import com.platform.survey.repositories.QuestionRepository;
import com.platform.survey.repositories.ReponseRepository;
import com.platform.survey.repositories.SondageRepository;
import com.platform.survey.repositories.UtilisateurRepository;
import com.platform.survey.sondageModule.reponse.DTOs.ReponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReponseService {

    private final ReponseRepository reponseRepository;
    private final QuestionRepository questionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final SondageRepository sondageRepository;

    private static final int XP_QUESTION_SIMPLE = 10;
    private static final int XP_QUESTION_MULTIPLE = 15;
    private static final int XP_QUESTION_TEXTE = 20;
    private static final int BONUS_XP_COMPLETION_SONDAGE = 50;

    @Autowired
    public ReponseService(ReponseRepository reponseRepository,
                          QuestionRepository questionRepository,
                          UtilisateurRepository utilisateurRepository,
                          SondageRepository sondageRepository) {
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.sondageRepository = sondageRepository;
    }


    @Transactional
    public Reponse enregistrerReponse(Long utilisateurId, Long questionId, String valeur) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
        Optional<Question> questionOpt = questionRepository.findById(questionId);

        if (utilisateurOpt.isPresent() && questionOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            Question question = questionOpt.get();

            Reponse reponse = new Reponse();
            reponse.setUtilisateur(utilisateur);
            reponse.setQuestion(question);
            reponse.setValeur(valeur);
            reponse.setDateReponse(LocalDateTime.now());

            Reponse reponseEnregistree = reponseRepository.save(reponse);

            attribuerPointsXPPourReponse(utilisateur, question);

            return reponseEnregistree;
        }

        throw new IllegalArgumentException("Utilisateur ou question non trouvé");
    }


    @Transactional
    public List<Reponse> enregistrerReponsesSondage(Long utilisateurId, Long sondageId, List<ReponseDTO> reponses) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
        Optional<Sondage> sondageOpt = sondageRepository.findById(sondageId);

        if (utilisateurOpt.isEmpty() || sondageOpt.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur ou sondage non trouvé");
        }

        Utilisateur utilisateur = utilisateurOpt.get();
        Sondage sondage = sondageOpt.get();
        List<Reponse> reponsesEnregistrees = new ArrayList<>();
        int totalXP = 0;

        for (ReponseDTO reponseDTO : reponses) {
            Optional<Question> questionOpt = questionRepository.findById(reponseDTO.getQuestionId());
            if (questionOpt.isPresent()) {
                Question question = questionOpt.get();

                if (!question.getSondage().getId().equals(sondageId)) {
                    throw new IllegalArgumentException("La question " + question.getId() + " n'appartient pas au sondage " + sondageId);
                }

                Reponse reponse = new Reponse();
                reponse.setUtilisateur(utilisateur);
                reponse.setQuestion(question);
                reponse.setValeur(reponseDTO.getValeur());
                reponse.setDateReponse(LocalDateTime.now());

                Reponse reponseEnregistree = reponseRepository.save(reponse);
                reponsesEnregistrees.add(reponseEnregistree);

                int pointsQuestion = calculerPointsXPPourQuestion(question);
                totalXP += pointsQuestion;
            }
        }

        List<Question> questionsNonRepondues = questionRepository.findBySondageId(sondageId).stream()
                .filter(q -> !reponses.stream().anyMatch(r -> r.getQuestionId().equals(q.getId())))
                .toList();

        if (questionsNonRepondues.isEmpty()) {
            totalXP += BONUS_XP_COMPLETION_SONDAGE;
        }

        utilisateur.setXp(utilisateur.getXp() + totalXP);
        utilisateurRepository.save(utilisateur);

        return reponsesEnregistrees;
    }


    public boolean aDejaReponduAuSondage(Long utilisateurId, Long sondageId) {
        return reponseRepository.existsByUtilisateurIdAndQuestionSondageId(utilisateurId, sondageId);
    }


    public List<Reponse> recupererReponsesUtilisateur(Long utilisateurId) {
        return reponseRepository.findByUtilisateurId(utilisateurId);
    }


    public List<Reponse> recupererDernieresReponsesUtilisateur(Long utilisateurId, int limit) {
        return reponseRepository.findByUtilisateurIdOrderByDateReponseDesc(utilisateurId, PageRequest.of(0, limit));
    }


    public List<Reponse> recupererReponsesSondage(Long sondageId) {
        return reponseRepository.findByQuestionSondageId(sondageId);
    }


    private void attribuerPointsXPPourReponse(Utilisateur utilisateur, Question question) {
        int pointsXP = calculerPointsXPPourQuestion(question);

        utilisateur.setXp(utilisateur.getXp() + pointsXP);
        utilisateurRepository.save(utilisateur);

    }


    private int calculerPointsXPPourQuestion(Question question) {
        TypeQuestion typeQuestion = question.getType();

        return switch (typeQuestion) {
            case CHOIX_SIMPLE -> XP_QUESTION_SIMPLE;
            case CHOIX_MULTIPLE -> XP_QUESTION_MULTIPLE;
            case TEXTE_LIBRE -> XP_QUESTION_TEXTE;
        };
    }



}