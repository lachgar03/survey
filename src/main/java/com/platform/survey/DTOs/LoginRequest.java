package com.platform.survey.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
