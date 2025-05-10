package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.AddToCartRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.UserResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.repository.UserRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.ProductVariationMapper;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProductVariationService productVariationService;
    private final ProductVariationMapper productVariationMapper;

    public UserService(UserRepository userRepository, ProductVariationService productVariationService, ProductVariationMapper productVariationMapper) {
        this.userRepository = userRepository;
        this.productVariationService = productVariationService;
        this.productVariationMapper = productVariationMapper;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user nao encontrado"));
    }

    @Transactional
    public ResponseEntity<UserResponseDTO> save(UserRequestDTO userRequestDTO, UserMapper userMapper) {
        if (loadUserByUsername(userRequestDTO.getEmail()) != null) // existe usuario com esse Email
            return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(userRequestDTO.getPassword()); // cryptografa a senha
        userRequestDTO.setPassword(encryptPassword); // set da senha cryptografada

        User user = userMapper.toEntity(userRequestDTO); // mapeia para entidade

        userRepository.save(user); // salva no banco

        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
