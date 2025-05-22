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

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
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

    /*
    * esse metodo nao lida com mudancas criticas do Usuario, como Email e Senha, principalmente Email
    * irei criar um Outro Metodo posteriormente para mudar esses dados e dar uma melhor resposat aao cliente
    *
    * */
    @PatchMapping(value = "/PATCH/{id}") ResponseEntity<UserResponseDTO> parcialUpdate(
            @PathVariable Long id,
            @RequestBody @Valid UserPatchRequestDTO userPatch
            ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.parcialUpdateUser(id, userPatch)));

    }

}
