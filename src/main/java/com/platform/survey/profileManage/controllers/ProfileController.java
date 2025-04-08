package com.platform.survey.profileManage.controllers;

import com.platform.survey.entites.Profil;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.profileManage.DTOs.PasswordChangeRequest;
import com.platform.survey.profileManage.DTOs.ProfileUpdateRequest;
import com.platform.survey.profileManage.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profileManage")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<Profil> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(profileService.getProfile(user.getUsername()));
    }

    @PutMapping("/update")
    public ResponseEntity<Utilisateur> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(user.getUsername(), request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Utilisateur> changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody PasswordChangeRequest request) {
        return ResponseEntity.ok(profileService.changePassword(user.getUsername(), request));
    }
}

