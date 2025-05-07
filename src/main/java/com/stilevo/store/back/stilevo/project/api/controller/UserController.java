package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.authentication.AuthenticationUserDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.UserResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import com.stilevo.store.back.stilevo.project.api.service.UserService;
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
    public ResponseEntity<UserResponseDTO> findById( @PathVariable Long id ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.findById(id)));
    }

    @PostMapping(value = "/POST/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userService.save(userRequestDTO, userMapper);
    }


}
