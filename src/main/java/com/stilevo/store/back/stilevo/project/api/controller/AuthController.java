package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AuthenticationUserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.LoginResponseDTO;
import com.stilevo.store.back.stilevo.project.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationUserRequestDTO userLogin) {
        return ResponseEntity.ok(authService.login(userLogin));
    }

}
