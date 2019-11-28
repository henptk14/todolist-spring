package com.pyikhine.todolist.services;

import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.entities.User;
import com.pyikhine.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService subject;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TodoService todoService;

    private final User user1 = User.builder()
            .id(1L)
            .fullName("user1")
            .username("user1")
            .email("user1@email.com")
            .password("password")
            .build();

    private final User user2 = user1.withUsername("user2").withEmail("user2").withId(2L);

    private Todo todo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        todo = Todo.builder()
                .id(1L)
                .todoTitle("test todo 1")
                .status("ACTIVE")
                .username(user1.getUsername())
                .user(user1)
                .build();
    }

    @Test
    void addTaskByTodoId_allValid() {

    }
}