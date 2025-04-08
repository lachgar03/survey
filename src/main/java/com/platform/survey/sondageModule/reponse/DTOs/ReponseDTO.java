package com.platform.survey.sondageModule.reponse.DTOs;

public class ReponseDTO {
    private Long questionId;
    private String valeur;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
