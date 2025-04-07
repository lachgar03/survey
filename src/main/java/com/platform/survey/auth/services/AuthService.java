package com.platform.survey.auth.services;



import com.platform.survey.auth.DTOs.AuthRequest;
import com.platform.survey.auth.DTOs.RegisterRequest;
import com.platform.survey.entites.Profil;
import com.platform.survey.entites.Utilisateur;
import com.platform.survey.enums.Role;
import com.platform.survey.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public Utilisateur register(RegisterRequest request) {

        Profil profil = new Profil();
        profil.setNom(request.getNom());
        profil.setPrenom(request.getPrenom());

        Utilisateur user = new Utilisateur();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.PARTICIPANT);
        user.setProfil(profil);
        user.setXp(0);

        return utilisateurRepository.save(user);
    }

    public Utilisateur authenticate(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        return utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow();
    }
}