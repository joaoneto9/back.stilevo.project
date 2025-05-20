package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserPatchRequestDTO;
import com.stilevo.store.back.stilevo.project.api.exception.InvalidAuthenticationUserException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AuthenticationUserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.LoginResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.UserResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import com.stilevo.store.back.stilevo.project.api.service.UserService;
import com.stilevo.store.back.stilevo.project.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private final UserMapper userMapper;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public UserController(UserMapper userMapper, UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping(value = "/GET/all")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll().stream()
                .map(userMapper::toResponse).toList());
    }

    @GetMapping(value = "/GET/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.findById(id)));
    }

    @PostMapping(value = "/POST/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userMapper.toResponse(userService.save(userMapper.toEntity(userRequestDTO))));
    }

    @PostMapping(value = "/POST/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationUserRequestDTO userLogin) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()); // vem do Spring Security

        try {
            var auth = authenticationManager.authenticate(usernamePassword); // autheticar esse novo email e senha que foram passadas
            if (auth.getPrincipal() instanceof User user) {
                String token = tokenService.generateToken(user);
                return ResponseEntity.ok(new LoginResponseDTO(userMapper.toResponse(user), token));
            } else {
                throw new InvalidAuthenticationUserException("Usuario nao authenticado, senha ou email invalidos.");
            }
        } catch (BadCredentialsException e) {
            // Captura uma falha de autenticação (senha ou email inválidos)
            throw new InvalidAuthenticationUserException("Usuário não autenticado, senha ou email inválidos.");
        }

    }

    // atualiza o User, o que e bom para que nao precise criar metodos especificos de atualizacaco.
    @PutMapping(value = "/UPDATE/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDTO userRequestDTO
    ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.updateUser(id, userMapper.toEntity(userRequestDTO))));
    }

    @DeleteMapping(value = "/DELETE/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.delete(id)));
    }

    @PatchMapping(value = "/PATCH/{id}") ResponseEntity<UserResponseDTO> parcialUpdate(
            @PathVariable Long id,
            @RequestBody @Valid UserPatchRequestDTO userPatch
            ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.parcialUpdateUser(id, userPatch)));

    }

}
