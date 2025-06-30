package com.stilevo.store.back.stilevo.project.api.service;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserPatchRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.UserResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.Cart;
import com.stilevo.store.back.stilevo.project.api.domain.entity.ProductVariation;
import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import com.stilevo.store.back.stilevo.project.api.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.exception.InvalidPasswordException;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.repository.UserRepository;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    // interface responsavel para dar um atch entre a senha nao criptografada e a senha criptografada

    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not Found")));
    }

    @Transactional
    public UserResponseDTO save(UserRequestDTO requestUser) {
        if (loadUserByUsername(requestUser.getEmail()) != null) // existe usuario com esse Email
            throw new ConflictException("email ja existente!");

        User user = userMapper.toEntity(requestUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Cart cart = new Cart();

        cart.setUser(user);
        user.setCart(cart);

        return userMapper.toResponse(userRepository.save(user)); // salva no banco
    }


    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO newUser) throws NotFoundException {
        User user = getReferenceById(id); // encontra o usuario que devemos setar os atributos.

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (newUser.getEndereco() != null) {
            user.setEndereco(enderecoRequestToEntity(newUser.getEndereco()));
        }

        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setRole(newUser.getRole());

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO delete(Long id) throws NotFoundException {
        User user = getReferenceById(id);

        userRepository.delete(user);

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO parcialUpdateUser(Long id, UserPatchRequestDTO userPatch) throws NotFoundException {
        User user = getReferenceById(id);

        if (!passwordEncoder.matches(userPatch.getPassword(), user.getPassword()))
            // primeiro parametro usa a senha nao criptografada e o segundo usa a senha criptografada
            throw new InvalidPasswordException("user send an invalid password, to change data");

        if (userPatch.getName() != null) {
            user.setName(userPatch.getName());
        }

        if (userPatch.getEndereco() != null) {
            user.setEndereco(enderecoRequestToEntity(userPatch.getEndereco()));
        }

        return userMapper.toResponse(userRepository.save(user));
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (UsernameNotFoundException exception) {
            throw new NotFoundException("User com esse email nao encontrado");
        }
    }


    // nao ficou muito legal, mas retedno ajustar
    private Endereco enderecoRequestToEntity(EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = new Endereco();

        endereco.setCep( enderecoRequestDTO.getCep() );
        endereco.setLogradouro( enderecoRequestDTO.getLogradouro() );
        endereco.setComplemento( enderecoRequestDTO.getComplemento() );
        endereco.setBairro( enderecoRequestDTO.getBairro() );
        endereco.setLocalidade( enderecoRequestDTO.getLocalidade() );
        endereco.setUf( enderecoRequestDTO.getUf() );
        endereco.setNumero( enderecoRequestDTO.getNumero() );
        endereco.setPontoReferencia( enderecoRequestDTO.getPontoReferencia() );

        return endereco;
    }

    @Transactional(readOnly = true)
    protected User getReferenceById(Long id) {
        try {
            return userRepository.getReferenceById(id);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundException("User not Found");
        }
    }
}
