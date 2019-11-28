package com.pyikhine.todolist.repository;

import com.pyikhine.todolist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private List<User> savedUsers;

    @BeforeEach
    void setup() {
        user1 = User.builder()
                .fullName("user1")
                .username("user1")
                .email("user1@email.com")
                .password("password").build();
        user2 = user1.withUsername("user2").withEmail("user2@email.com");
        savedUsers = userRepository.saveAll(Arrays.asList(user1, user2));
    }

    @Test
    void findByUsernameOrEmail_validUsername() {
        Optional<User> actual = userRepository.findByUsernameOrEmail("user2");

        assertTrue(actual.isPresent());
        assertThat(actual.get().getUsername()).isEqualTo(user2.getUsername());
        assertThat(actual.get().getEmail()).isEqualTo(user2.getEmail());
    }

    @Test
    void findByUsernameOrEmail_validEmail() {
        Optional<User> actual = userRepository.findByUsernameOrEmail("user1@email.com");

        assertTrue(actual.isPresent());
        assertThat(actual.get().getUsername()).isEqualTo("user1");
        assertThat(actual.get().getEmail()).isEqualTo("user1@email.com");
    }

    @Test
    void findByUsernameOrEmail_invalidUsername() {
        Optional<User> actual = userRepository.findByUsernameOrEmail("invalid");

        assertFalse(actual.isPresent());
    }

    @Test
    void findById_validId() {
        Optional<User> actual = userRepository.findById(savedUsers.get(0).getId());

        assertTrue(actual.isPresent());
        assertThat(actual.get().getEmail()).isEqualTo(savedUsers.get(0).getEmail());
    }

    @Test
    void findById_invalidId() {
        Optional<User> actual = userRepository.findById(0L);

        assertFalse(actual.isPresent());
    }
}