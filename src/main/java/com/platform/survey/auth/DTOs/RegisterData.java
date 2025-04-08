package com.platform.survey.auth.DTOs;

import com.platform.survey.entites.Profil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterData {
    private String email;
    private String password;
    private Profil profil;
}
