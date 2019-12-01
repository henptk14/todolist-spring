package com.pyikhine.todolist.repository;

import com.pyikhine.todolist.entities.Task;
import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoAndTaskCascadeTest {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TaskRepository taskRepository;

    private final String USERNAME = "testusername";
    private User user;
    private Todo todo1;

    @BeforeEach
    void setup() {
        user = User.builder()
                .fullName("test")
                .username(USERNAME)
                .password("password")
                .email("test@email.com")
                .build();

        todo1 = Todo.builder()
                .todoTitle("test todo 1")
                .status("TEST")
                .username(USERNAME)
                .user(user)
                .build();
    }

    @Test
    void save_cascadeSaveTask() {
        Task task = Task.builder().taskDescription("test task").status("TEST").build();

        Todo savedTodo = todoRepository.save(todo1);
        savedTodo.addTask(task);
        savedTodo = todoRepository.save(savedTodo);

        assertEquals(1, savedTodo.getTasks().size());

        Optional<Task> savedTask = taskRepository.findById(savedTodo.getTasks().get(0).getId());

        assertTrue(savedTask.isPresent());
        assertTrue(savedTask.get().getId() != null);
        assertThat(savedTask.get().getTodo()).isEqualTo(savedTodo);
    }

    @Test
    void update_cascadeUpdateTask() {
        Task task1 = Task.builder().taskDescription("task1").status("TEST").build();
        Task task2 = Task.builder().taskDescription("task2").status("TEST").build();

        todo1.addTask(task1);
        todo1.addTask(task2);
        Todo savedTodo = todoRepository.save(todo1);

        assertEquals(2, savedTodo.getTasks().size());

        Long savedTaskId = savedTodo.getTasks().get(1).getId();
        savedTodo.getTasks().get(1).setTaskDescription("Updated task2");
        savedTodo = todoRepository.save(savedTodo);

        assertThat(savedTodo.getTasks().get(1)).isEqualTo(taskRepository.findById(savedTaskId).get());
        assertEquals("Updated task2", taskRepository.findById(savedTaskId).get().getTaskDescription());
    }
}
