package com.alex.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.user_service.details.UserDetailsImpl;
import com.alex.user_service.dtos.AuthResponse;
import com.alex.user_service.dtos.LoginRequest;
import com.alex.user_service.dtos.RegisterRequest;
import com.alex.user_service.jwt.JwtUtil;
import com.alex.user_service.model.User;
import com.alex.user_service.repository.UserRepository;


@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse authenticate(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        if(userRepository.findByUsername(registerRequest.username()).isPresent()){
            return ResponseEntity.badRequest()
                .body("Erro: Nome de usuário já está em uso!");
        }

        String passwordEncoded = passwordEncoder.encode(registerRequest.password());

        User user = new User(registerRequest.username(), passwordEncoded);
        userRepository.save(user);

        return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public String getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "Usuário autenticado: " + userDetails.getUsername();
    }
}
