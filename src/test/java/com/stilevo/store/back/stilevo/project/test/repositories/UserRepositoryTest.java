package com.stilevo.store.back.stilevo.project.test.repositories;

import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.domain.enums.UserRole;
import com.stilevo.store.back.stilevo.project.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void registerUser() {User user = new User();

        user.setName("joao neto");
        user.setEmail("joaoneto@gmail.com");
        user.setPassword("senha");
        user.setRole(UserRole.USER); // usuario normal


        User userSalvo = userRepository.saveAndFlush(user);

        Assertions.assertNotNull(userSalvo.getId());
        //significa que o id nao e nulo e foi salvo
        assertEquals(userSalvo.getName(), user.getName());
        // significa que salvou no repositorio e que tem o mesmo nome, confirmando
    }

    @Test
    void registerUserConflict() {
        User user1 = new User();
        user1.setName("João");
        user1.setEmail("joao@gmail.com");
        user1.setPassword("senha");
        user1.setRole(UserRole.USER);

        userRepository.saveAndFlush(user1);

        User user2 = new User();
        user2.setName("Outro João");
        user2.setEmail("joao@gmail.com");  // mesmo email

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });

    }

    @Test
    void findUserById() {
        User user1 = new User();

        user1.setName("João");
        user1.setEmail("joao@gmail.com");
        user1.setPassword("senha");
        user1.setRole(UserRole.USER);

        User newUser = userRepository.saveAndFlush(user1);

        assertEquals(userRepository.findById(newUser.getId()).get().getEmail(), newUser.getEmail());
        // compara o email do usuario recem-cadastrado, isso confirma que o findById da certo
    }

    @Test
    void findUserNotFoundById() {
        assertTrue(userRepository.findById(1L).isEmpty());
        // noa tem nenhum user registrado
    }

    @Test
    void updateUser() {
        User user1 = new User();

        user1.setName("João");
        user1.setEmail("joao@gmail.com");
        user1.setPassword("senha");
        user1.setRole(UserRole.USER);

        User user2 = userRepository.saveAndFlush(user1);

        user2.setName("novo nome");

        userRepository.saveAndFlush(user2);

        assertEquals(userRepository.findById(user2.getId()).get().getName(), "novo nome");
        // isso garante que o user com o id inicialmente cadasreado, agora foi atualizado com novo nome
    }

}
