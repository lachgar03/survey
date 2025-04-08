package com.platform.survey.auth.controllers;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.survey.auth.DTOs.AuthRequest;
import com.platform.survey.auth.DTOs.AuthResponse;
import com.platform.survey.auth.DTOs.RegisterRequest;
import com.platform.survey.auth.config.JwtTokenUtil;
import com.platform.survey.auth.services.AuthService;
import com.platform.survey.entites.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // ou * pour tester
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        Utilisateur user = authService.register(request);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token, user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Utilisateur user = authService.authenticate(request);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        AuthResponse response = new AuthResponse(token, user);

        // Debug the response
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(response);
            System.out.println("JSON Response: " + jsonResponse);
        } catch (Exception e) {
            System.err.println("Error serializing response: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}