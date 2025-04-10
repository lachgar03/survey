package com.platform.survey.profileManage.controllers;

import com.platform.survey.entites.Profil;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.profileManage.DTOs.PasswordChangeRequest;
import com.platform.survey.profileManage.DTOs.ProfileResponse;
import com.platform.survey.profileManage.DTOs.ProfileUpdateRequest;
import com.platform.survey.profileManage.services.ProfileService;
import com.platform.survey.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profileManage")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Profil profil = utilisateur.getProfil();
        return ResponseEntity.ok(new ProfileResponse(utilisateur.getEmail(), profil));
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

