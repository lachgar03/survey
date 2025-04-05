package com.platform.survey.DTOs;


import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
