package com.pyikhine.todolist.controller;

import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.services.MapValidationErrorService;
import com.pyikhine.todolist.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createTodo(@Valid @RequestBody Todo todo, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Todo todo1 = todoService.saveOrUpdateTodo(todo, principal.getName());
        return new ResponseEntity<>(todo1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<Todo> findAllTodo(Principal principal) {
        return todoService.findAllTodoByUsername(principal.getName());
    }

    @PatchMapping("")
    public ResponseEntity<?> updateTodo(@Valid @RequestBody Todo todo, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Todo todo1 = todoService.saveOrUpdateTodo(todo, principal.getName());
        return new ResponseEntity<>(todo1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable String id, Principal principal){
        todoService.deleteByTodoId(id, principal.getName());

        return new ResponseEntity<>("Todo with ID: '" + id + "' is deleted", HttpStatus.OK);
    }
}
