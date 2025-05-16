package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
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

        EnderecoService.getCep(endereco.getCep()); // valida o Cep antes de mandar, so por preocausao.

        user.setEndereco(endereco);

        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = findById(id); // encontra o usuario que devemos setar os atributos.

        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());

        return userRepository.save(user); // atualiza o usuario
    }
}
