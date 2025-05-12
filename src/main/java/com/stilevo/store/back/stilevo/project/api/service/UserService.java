package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.controller.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.controller.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoViacepRequest;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import com.stilevo.store.back.stilevo.project.api.domain.repository.UserRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.EnderecoMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user nao encontrado"));
    }

    @Transactional
    public User save(User user) {
        if (loadUserByUsername(user.getEmail()) != null) // existe usuario com esse Email
            throw new ConflictException("email ja existente!");

        String encryptPassword = new BCryptPasswordEncoder().encode(user.getPassword()); // cryptografa a senha
        user.setPassword(encryptPassword); // set da senha cryptografada

        return userRepository.save(user); // salva no banco
    }

    @Transactional
    public User putEndereco(Endereco endereco, Long id) {
        User user = findById(id);

        user.setEndereco(endereco);

        userRepository.save(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

}
