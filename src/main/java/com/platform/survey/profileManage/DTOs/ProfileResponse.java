package com.platform.survey.profileManage.DTOs;

import com.platform.survey.entites.Profil;

public class ProfileResponse {
    private String email;
    private Profil profil;

    // constructors
    public ProfileResponse(String email, Profil profil) {
        this.email = email;
        this.profil = profil;
    }

    // getters & setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}
