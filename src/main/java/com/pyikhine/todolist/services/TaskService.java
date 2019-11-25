package com.pyikhine.todolist.services;

import com.pyikhine.todolist.entities.Task;
import com.pyikhine.todolist.entities.Todo;
import com.pyikhine.todolist.exceptions.TaskNotFoundException;
import com.pyikhine.todolist.repository.TaskRepository;
import com.pyikhine.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TodoService todoService;

    public Task addTaskByTodoId(String todoId, Task task, String username) {
        Todo todo = todoService.findByTodoId(todoId, username);
        task.setTodo(todo);
        Task savedTask = taskRepository.save(task);
        todo.getTasks().add(savedTask);
        todoService.saveOrUpdateTodo(todo, username);
        return savedTask;
    }

    public Iterable<Task> getTasksByTodoId(String todoId, String username) {
        Todo todo = todoService.findByTodoId(todoId, username);
        return todo.getTasks();
    }

    public Task updateTaskById(String taskId, Task task) {
        Task temp = findTaskById(taskId);
        temp.setStatus(task.getStatus());
        temp.setTaskDescription(task.getTaskDescription());
        return taskRepository.save(temp);
    }

    public void deleteTaskById(String taskId) {
        taskRepository.delete(findTaskById(taskId));
    }

    private Task findTaskById(String taskId) {
        long realId;
        try {
            realId = Long.parseLong(taskId);
        } catch (NumberFormatException ex) {
            throw new TaskNotFoundException("Task ID: '" + taskId + "' is of invalid format");
        }
        Optional<Task> task = taskRepository.findById(realId);
        if (task.isPresent()) {
            return task.get();
        }
        throw new TaskNotFoundException("Task ID: '" + taskId + "' does not exist");
    }
}
