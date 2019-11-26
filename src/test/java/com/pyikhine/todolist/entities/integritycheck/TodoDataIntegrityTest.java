package com.pyikhine.todolist.entities.integritycheck;

import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoDataIntegrityTest {
    private User user;
    private Todo todo;

    @BeforeEach
    void setup() {
        user = User.builder()
                .fullName("John Wick")
                .username("johnwick")
                .email("johnwick@email.com")
                .password("password")
                .confirmPassword("password")
                .build();

        todo = Todo.builder()
                .todoTitle("Todo test")
                .user(user)
                .username(user.getUsername())
                .build();
    }

    @Test
    void check_allValid() {
        String actual = TodoDataIntegrity.check(todo);
        assertEquals("", actual);
    }

    @Test
    void check_nullTodoTitle() {
        todo.setTodoTitle(null);

        String actual = TodoDataIntegrity.check(todo);
        assertEquals("Todo Title is null.", actual);
    }

    @Test
    void check_nullUser() {
        todo.setUser(null);

        String actual = TodoDataIntegrity.check(todo);
        assertEquals("Todo's User is null.", actual);
    }

    @Test
    void check_nullUsername() {
        todo.setUsername(null);

        String actual = TodoDataIntegrity.check(todo);
        assertEquals("Todo's Username is null.", actual);
    }

    @Test
    void check_nullUserAndNullUsername() {
        todo.setUser(null);
        todo.setUsername(null);

        String actual = TodoDataIntegrity.check(todo);
        assertEquals("Todo's User is null.", actual);
    }
}