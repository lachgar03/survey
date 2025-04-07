package com.platform.survey.profileManage.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class PasswordChangeRequest {
    @NotBlank
    private String currentPassword;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;
    @NotBlank
    private String confirmPassword;
}
