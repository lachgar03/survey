package com.platform.survey.auth.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
