package com.stilevo.store.back.stilevo.project.test.services;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserPatchRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.UserRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.response.UserResponseDTO;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.enums.UserRole;
import com.stilevo.store.back.stilevo.project.api.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.exception.InvalidPasswordException;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
import com.stilevo.store.back.stilevo.project.api.mapper.UserMapper;
import com.stilevo.store.back.stilevo.project.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void saveUserTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user);

        assertNotNull(userSaved.getId()); // o id nao e null mais
        assertEquals(userSaved.getEmail(), user.getEmail()); // o email e o mesmo
    }

    @Test
    void saveUserButEmailExistsTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        userService.save(user);

        assertThrows(ConflictException.class, () -> userService.save(user));
    }

    @Test
    void findUserByIdTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user);

        assertEquals(userService.findById(userSaved.getId()).getEmail(), user.getEmail()); // existe User
    }

    @Test
    void cantFindByIdTest() {
        assertThrows(NotFoundException.class, () -> userService.findById(0L)); // nunca existe um id 0
    }

    @Test
    void updateUserTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user); // salva no banco

        assertEquals(userSaved.getName(), user.getName()); // teste so pra ter certeza que salvoi no banco

        // vamos atualizar agora

        UserRequestDTO userUpdate = new UserRequestDTO();

        userUpdate.setName("novo nome");
        userUpdate.setEmail("emailnovo@gmail.com");
        userUpdate.setPassword(user.getPassword());
        userUpdate.setRole(user.getRole());

        UserResponseDTO userUpdated = userService.updateUser(userSaved.getId(), userUpdate); // atualiza o user

        assertEquals(userUpdated.getId(), userSaved.getId()); // precisam ter o mesmo id para sinalizar que trocou o mesmo user
        assertEquals(userUpdated.getName(), "novo nome"); // teste da mudanca do nome
        assertEquals(userUpdated.getEmail(), "emailnovo@gmail.com"); // teste da mudanca do email
    }

    @Test
    void deleteUserTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user);

        UserResponseDTO userDeleted = userService.delete(userSaved.getId()); // deleta o user que acabou de ser salvo

        assertEquals(userSaved, userDeleted);
        // deve ser o mesmo user que foi salvo
        assertThrows(NotFoundException.class, () -> userService.findById(userSaved.getId()));
        // deve lancar a exessao pois o user foi deletado
    }

    @Test
    void parcialUpdateErrorPasswordUserTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user);

        UserPatchRequestDTO userPatch = new UserPatchRequestDTO();

        userPatch.setName("novo nome");
        userPatch.setPassword("senha diferente, gera erro");
        userPatch.setEndereco(null); // endereco null

        assertThrows(InvalidPasswordException.class, () -> userService.parcialUpdateUser(userSaved.getId(), userPatch));
    }

    @Test
    void parcialUpdateUserTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user); // lembrra que ta com a sneha criptografada

        UserPatchRequestDTO userPatch = new UserPatchRequestDTO();

        userPatch.setName("novo nome");
        userPatch.setPassword("senha");

        EnderecoRequestDTO endereco = new EnderecoRequestDTO();

        endereco.setUf("cg");
        endereco.setNumero(999);
        endereco.setLocalidade("sei nao mizera");
        endereco.setComplemento("casa de cachorro");
        endereco.setCep("55555333");
        endereco.setBairro("bairro");
        endereco.setPontoReferencia("logo ali");

        userPatch.setEndereco(endereco); // endereco nao null, tem que alterar

        try {
            UserResponseDTO userUpdated = userService.parcialUpdateUser(userSaved.getId(), userPatch);
            assertEquals(userUpdated.getName(), userPatch.getName());
            // isso significa que alterou o nome
            assertEquals(userService.findById(userSaved.getId()).getEndereco().getCep(), userUpdated.getEndereco().getCep());
            // verifica que mudou o endereco do user com id especificado
        } catch (InvalidPasswordException e) {
            System.out.println("deu erro, user mandou a senha errada");
            assertTrue(false); // demonstrar que deu errado
        } catch (NotFoundException e) {
            System.out.println("deu erro, user nao encontrado");
            assertTrue(false); // demonstarr que deu erro
        }
    }

    @Test
    void parcialUpdateUserNullTest() {
        UserRequestDTO user = new UserRequestDTO();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        UserResponseDTO userSaved = userService.save(user);

        UserPatchRequestDTO userPatch = new UserPatchRequestDTO();

        userPatch.setName(null);
        userPatch.setPassword("senha");
        userPatch.setEndereco(null); // endereco null, nao altera

        try {
            UserResponseDTO userUpdated = userService.parcialUpdateUser(userSaved.getId(), userPatch);
            assertEquals(userUpdated.getName(), userSaved.getName());
            // isso significa que nao alterou o nome, porque era null
            assertEquals(userSaved.getEndereco(), userUpdated.getEndereco());
            // como o UserPatch e null, nao devia alterar o endereco, manetndo o endereco anterior
        } catch (InvalidPasswordException e) {
            System.out.println("deu erro, user mandou a senha errada");
            assertTrue(false);
        } catch (NotFoundException e) {
            System.out.println("deu erro, user nao encontrado");
            assertTrue(false);
        }
    }

}
