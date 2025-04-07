package com.platform.survey.profileManage.services;

import com.platform.survey.entites.Profil;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.profileManage.DTOs.PasswordChangeRequest;
import com.platform.survey.profileManage.DTOs.ProfileUpdateRequest;
import com.platform.survey.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public Utilisateur updateProfile(String email, ProfileUpdateRequest request){
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Profil profil = user.getProfil();
        if (request.getNom() != null) profil.setNom(request.getNom());
        if (request.getPrenom() != null) profil.setPrenom(request.getPrenom());
        if (request.getMetier() != null) profil.setMetier(request.getMetier());
        if (request.getRegion() != null) profil.setRegion(request.getRegion());
        if (request.getAge() != null) profil.setAge(request.getAge());
        if (request.getNumeroTelephone() != null) profil.setNumeroTelephone(request.getNumeroTelephone());
        if (request.getInterets() != null) profil.setInterets(request.getInterets());

        return utilisateurRepository.save(user);
    }
    public Utilisateur changePassword(String email, PasswordChangeRequest request) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe actuel incorrect");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Les nouveaux mots de passe ne correspondent pas");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return utilisateurRepository.save(user);
    }

    public Profil getProfile(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"))
                .getProfil();
    }


}
