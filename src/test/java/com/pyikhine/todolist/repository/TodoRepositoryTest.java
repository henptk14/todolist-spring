package com.pyikhine.todolist.repository;

import com.pyikhine.todolist.entities.Todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTest {
    @Autowired
    TodoRepository todoRepository;

    private final String USERNAME = "testusername";

    @Test
    void findAllByUsername_validUsername() {
        Todo todo1 = Todo.builder().todoTitle("test todo1").status("TEST").username(USERNAME).build();
        Todo todo2 = todo1.withTodoTitle("test todo2");
        Todo todo3 = todo1.withTodoTitle("test todo3");
        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));

        List<Todo> actual = (List<Todo>) todoRepository.findAllByUsername(USERNAME);

        assertEquals(3, actual.size());
        assertThat(actual).containsExactlyInAnyOrder(todo1, todo2, todo3);
    }

    @Test
    void findAllByUsername_invalidUsername() {
        Todo todo1 = Todo.builder().todoTitle("test todo1").status("TEST").username(USERNAME).build();
        Todo todo2 = todo1.withTodoTitle("test todo2");
        Todo todo3 = todo1.withTodoTitle("test todo3");
        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));

        List<Todo> actual = (List<Todo>) todoRepository.findAllByUsername("someusername");

        assertEquals(0, actual.size());
    }

    @Test
    void findByIdAndUsername_validIdAndUsername() {
        Todo todo1 = Todo.builder().todoTitle("test todo1").status("TEST").username(USERNAME).build();
        Todo todo2 = todo1.withTodoTitle("test todo2").withUsername("testusername2");
        Todo todo3 = todo1.withTodoTitle("test todo3");
        List<Todo> savedTodos = todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));

        Optional<Todo> actual = todoRepository.findByIdAndUsername(savedTodos.get(1).getId(), "testusername2");

        assertTrue(actual.isPresent());
        assertThat(actual.get().getUsername()).isEqualTo("testusername2");
    }

    @Test
    void findByIdAndUsername_invalidIdValidUsername() {
        Todo todo1 = Todo.builder().todoTitle("test todo1").status("TEST").username(USERNAME).build();
        Todo todo2 = todo1.withTodoTitle("test todo2").withUsername("testusername2");
        Todo todo3 = todo1.withTodoTitle("test todo3");
        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));

        Optional<Todo> actual = todoRepository.findByIdAndUsername(1L, "testusername2");

        assertTrue(!actual.isPresent());
    }

    @Test
    void findByIdAndUsername_validIdInvalidUsername() {
        Todo todo1 = Todo.builder().todoTitle("test todo1").status("TEST").username(USERNAME).build();
        Todo todo2 = todo1.withTodoTitle("test todo2");
        Todo todo3 = todo1.withTodoTitle("test todo3");
        List<Todo> savedTodos = todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));

        Optional<Todo> actual = todoRepository.findByIdAndUsername(savedTodos.get(0).getId(), "someusername");

        assertTrue(!actual.isPresent());
    }
}