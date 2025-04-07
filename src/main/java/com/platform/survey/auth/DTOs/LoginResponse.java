package com.platform.survey.auth.DTOs;

import com.platform.survey.entites.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Utilisateur utilisateur;
}
