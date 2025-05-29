package com.stilevo.store.back.stilevo.project.test.services;

import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.enums.UserRole;
import com.stilevo.store.back.stilevo.project.api.exception.ConflictException;
import com.stilevo.store.back.stilevo.project.api.exception.NotFoundException;
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

    @Test
    void saveUserTest() {
        User user = new User();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        User userSaved = userService.save(user);

        assertNotNull(userSaved.getId()); // o id nao e null mais
        assertEquals(userSaved.getEmail(), user.getEmail()); // o email e o mesmo
    }

    @Test
    void saveUserButEmailExistsTest() {
        User user = new User();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        userService.save(user);

        assertThrows(ConflictException.class, () -> userService.save(user));
    }

    @Test
    void findUserByIdTest() {
        User user = new User();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        User userSaved = userService.save(user);

        assertEquals(userService.findById(userSaved.getId()).getEmail(), user.getEmail()); // existe User
    }

    @Test
    void cantFindByIdTest() {
        assertThrows(NotFoundException.class, () -> userService.findById(0L)); // nunca existe um id 0
    }

    @Test
    void updateUserTest() {
        User user = new User();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal

        User userSaved = userService.save(user); // salva no banco

        assertEquals(userSaved.getName(), user.getName()); // teste so pra ter certeza que salvoi no banco

        // vamos atualizar agora

        userSaved.setName("novo nome");
        userSaved.setEmail("emailnovo@gmail.com");

        User userUpdated = userService.updateUser(userSaved.getId(), userSaved); // atualiza o user

        assertEquals(userUpdated.getId(), userSaved.getId()); // precisam ter o mesmo id para sinalizar que trocou o mesmo user
        assertEquals(userUpdated.getName(), "novo nome"); // teste da mudanca do nome
        assertEquals(userUpdated.getEmail(), "emailnovo@gmail.com"); // teste da mudanca do email
    }

}
