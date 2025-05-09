package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.authentication.AuthenticationUserDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.ProductVariationRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.LoginResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.UserResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import com.stilevo.store.back.stilevo.project.api.service.UserService;
import com.stilevo.store.back.stilevo.project.api.config.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
        return userService.save(userRequestDTO, userMapper);
    }

    @PostMapping(value = "POST/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationUserDTO userLogin) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()); // vem do Spring Security
        var auth = authenticationManager.authenticate(usernamePassword); // autheticar esse novo email e senha que foram passadas

        String token = tokenService.generateToken((User) auth.getPrincipal()); // gera o token
        return ResponseEntity.ok(new LoginResponseDTO(token)); // quando logar, recebe o token
    }

//    @PutMapping(value = "UPDATE/add/product/{id}")
//    public ResponseEntity<UserResponseDTO> addProduct(
//            @PathVariable Long id,
//            @RequestBody @Valid ProductVariationRequestDTO productVariationRequestDTO
//    ) {
//        return ResponseEntity.ok(userMapper.toResponse(userService.updateProduct(id, productVariationRequestDTO)));
//    }




}
