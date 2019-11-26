package com.pyikhine.todolist.services;

import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.entities.User;
import com.pyikhine.todolist.exceptions.TodoDataIntegrityBrokenException;
import com.pyikhine.todolist.repository.TodoRepository;
import com.pyikhine.todolist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TodoService subject;

    @Captor
    ArgumentCaptor<Todo> captor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveOrUpdateTodo_SaveNewTodoAllValid() {
        String username = "johnwick";
        String fullName = "John Wick";
        String email = "johnwick@email.com";

        User.UserBuilder userBuilder = User.builder()
                .id(1L)
                .fullName("John Wick")
                .username(username)
                .email(email)
                .fullName(fullName)
                .password("password")
                .confirmPassword("password")
                .createdAt(new Date());

        Todo todoFromClient = Todo.builder()
                .todoTitle("No more id or so i thought")
                .status("ACTIVE")
                .build();

        when(userRepository.findByUsernameOrEmail(username, "")).thenReturn(
                Optional.of(userBuilder.build()));

        subject.saveOrUpdateTodo(todoFromClient, username);
        verify(todoRepository).save(captor.capture());
        assertThat(captor.getValue().getUsername()).isEqualTo(username);
        assertThat(captor.getValue().getTodoTitle()).isEqualTo("No more id or so i thought");

        assertThat(todoFromClient.getUsername()).isEqualTo(username);
        assertThat(todoFromClient.getUser().getEmail()).isEqualTo(email);
    }

    @Test
    void saveOrUpdateTodo_UsernameDoesNotExist() {
        String username = "johnwick";
        String fullName = "John Wick";
        String email = "johnwick@email.com";

        Todo todoFromClient = Todo.builder()
                .todoTitle("No more id or so i thought")
                .status("ACTIVE")
                .build();

        String errorMessage = "Username: " + username + " is not found";
        when(userRepository.findByUsernameOrEmail(username, ""))
                .thenThrow(
                        new UsernameNotFoundException(errorMessage)
                );

        assertThrows(UsernameNotFoundException.class,
                () -> subject.saveOrUpdateTodo(todoFromClient, username),
                errorMessage);
    }

    @Test
    void saveOrUpdateTodo_UpdateExistingTodoWithDataIntegrityIssue() {
        String username = "johnwick";
        String fullName = "John Wick";
        String email = "johnwick@email.com";

        User.UserBuilder userBuilder = User.builder()
                .id(1L)
                .fullName("John Wick")
                .username(username)
                .email(email)
                .fullName(fullName)
                .password("password")
                .confirmPassword("password")
                .createdAt(new Date());

        Todo todoFromClient = Todo.builder()
                .id(1L)
                .todoTitle("Test")
                .status("ACTIVE")
                .build();

        when(userRepository.findByUsernameOrEmail(username, ""))
                .thenReturn(Optional.of(userBuilder.build()));

        assertThrows(TodoDataIntegrityBrokenException.class,
                () -> subject.saveOrUpdateTodo(todoFromClient, username),
                "Todo's User is null."
                );
    }
}