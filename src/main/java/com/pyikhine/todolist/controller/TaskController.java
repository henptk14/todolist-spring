package com.pyikhine.todolist.controller;

import com.pyikhine.todolist.entities.Task;
import com.pyikhine.todolist.services.MapValidationErrorService;
import com.pyikhine.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{todoId}")
    public ResponseEntity<?> addTaskByTodoId(@PathVariable String todoId, @Valid @RequestBody Task task, BindingResult result, Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        return new ResponseEntity<>(taskService.addTaskByTodoId(todoId, task, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{todoId}")
    public Iterable<Task> getTasksByTodoId(@PathVariable String todoId, Principal principal) {
        return taskService.getTasksByTodoId(todoId, principal.getName());
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTaskById(@PathVariable String taskId) {
        taskService.deleteTaskById(taskId);

        return new ResponseEntity<>("Task with ID: '" + taskId + "' is deleted", HttpStatus.OK);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<?> updateTaskById(@PathVariable String taskId, @Valid @RequestBody Task task, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        return new ResponseEntity<>(taskService.updateTaskById(taskId, task), HttpStatus.OK);
    }
}
