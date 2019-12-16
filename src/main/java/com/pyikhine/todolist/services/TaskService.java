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


}
