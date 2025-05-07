package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.authentication.AuthenticationUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationUserDTO userLogin) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()); // vem do Spring Security
        var auth = authenticationManager.authenticate(usernamePassword); // autheticar esse novo email e senha que foram passadas

        return ResponseEntity.ok().build();
    }

}
