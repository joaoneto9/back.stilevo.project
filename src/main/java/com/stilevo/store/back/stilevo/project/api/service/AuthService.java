package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AuthenticationUserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.LoginResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.exception.InvalidAuthenticationUserException;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final UserMapper userMapper;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserMapper userMapper, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDTO login(@RequestBody @Valid AuthenticationUserRequestDTO userLogin) {
        try {
            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()); // vem do Spring Security

            var auth = authenticationManager.authenticate(usernamePassword); // autheticar esse novo email e senha que foram passadas

            if (auth.getPrincipal() instanceof User user) {
                String token = tokenService.generateToken(user);
                return new LoginResponseDTO(userMapper.toResponse(user), token);
            } else {
                throw new InvalidAuthenticationUserException("Usuario nao authenticado, senha ou email invalidos.");
            }
        } catch (BadCredentialsException e) {
            // Captura uma falha de autenticação (senha ou email inválidos)
            throw new InvalidAuthenticationUserException("Usuário não autenticado, senha ou email inválidos.");
        }
    }
}
