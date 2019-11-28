package com.pyikhine.todolist.services;

import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.entities.User;
import com.pyikhine.todolist.entities.integritycheck.TodoDataIntegrity;
import com.pyikhine.todolist.exceptions.TodoDataIntegrityBrokenException;
import com.pyikhine.todolist.exceptions.TodoNotFoundException;
import com.pyikhine.todolist.repository.TodoRepository;
import com.pyikhine.todolist.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public Todo saveOrUpdateTodo(Todo todo, String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " is not found")
        );
        if (todo.getId() == null) {
            todo.setUser(user);
            todo.setUsername(username);
        } else {
            String integrity = TodoDataIntegrity.updateCheck(todo);
            if (!integrity.isEmpty()) {
                throw new TodoDataIntegrityBrokenException(integrity);
            }
            if (!todo.getUsername().equals(username)) {
                throw new TodoNotFoundException("Todo not found in your account");
            }
        }
        return todoRepository.save(todo);
    }

    public Iterable<Todo> findAllTodoByUsername(String username) {
        return todoRepository.findAllByUsername(username);
    }

    public void deleteByTodoId(String id, String username) {
        todoRepository.delete(findByTodoId(id, username));
    }

    public Todo findByTodoId(String id, String username) {
        long realId;
        try {
            realId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new TodoNotFoundException("Todo ID: '" + id + "' is of invalid format.");
        }

        return todoRepository.findByIdAndUsername(realId, username).orElseThrow(
                () -> new TodoNotFoundException("Todo ID: '" + id + "' does not exist or does not belong to the logged in user.")
        );
    }
}
