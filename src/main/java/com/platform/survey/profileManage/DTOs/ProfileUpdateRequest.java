package com.platform.survey.profileManage.DTOs;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProfileUpdateRequest {
    @Size(max=100)
    private String nom;
    @Size(max=100)
    private String prenom;
    private String metier;
    private String region;
    private Integer age;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    private String numeroTelephone;
    private List<String> interets;
}
