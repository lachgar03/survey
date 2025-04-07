package com.platform.survey.auth.DTOs;

import com.platform.survey.entites.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Utilisateur utilisateur;
}
