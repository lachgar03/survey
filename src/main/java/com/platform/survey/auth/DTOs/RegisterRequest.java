package com.platform.survey.auth.DTOs;

import com.platform.survey.entites.Profil;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String nom;
    private String prenom;
}

