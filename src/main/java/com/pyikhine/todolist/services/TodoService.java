package com.pyikhine.todolist.services;

import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.entities.User;
import com.pyikhine.todolist.exceptions.TodoDataIntegrityBrokenException;
import com.pyikhine.todolist.exceptions.TodoNotFoundException;
import com.pyikhine.todolist.repository.TodoRepository;
import com.pyikhine.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public Todo saveOrUpdateTodo(Todo todo, String username) {
        User user = userRepository.findByUsernameOrEmail(username, "").orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " is not found")
        );
        if (todo.getId() == null) {
            todo.setUser(user);
            todo.setUsername(username);
        } else {
            String integrity = todoDataIntegrityCheck(todo);
            if (!integrity.isEmpty()) {
                throw new TodoDataIntegrityBrokenException(integrity);
            }
            if (!todo.getUsername().equals(username)) {
                throw new TodoNotFoundException("Todo not found in your account");
            }
        }
        return todoRepository.save(todo);
    }

    public Iterable<Todo> findAllTodosByUsername(String username) {
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
            throw new TodoNotFoundException("Todo ID: '" + id + "' is of invalid format");
        }
        Optional<Todo> todo = todoRepository.findByIdAndUsername(realId, username);
        if (todo.isPresent()) {
            return todo.get();
        }
        throw new TodoNotFoundException("Todo ID: '" + id + "' does not exist or does not belong to the logged in user.");
    }

    public String todoDataIntegrityCheck(Todo todo) {
        if (StringUtils.isEmpty(todo.getTodoTitle())) {
            return "Todo Title is null.";
        }
        if (todo.getUser() == null) {
            return "Todo's User is null.";
        }
        if (StringUtils.isEmpty(todo.getUsername())) {
            return "Todo's Username is null.";
        }
        return "";
    }
}
