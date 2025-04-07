package com.platform.survey.auth.DTOs;

import com.platform.survey.enums.Role;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Role role;
}
