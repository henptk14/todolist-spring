package com.pyikhine.todolist.repository;

import com.pyikhine.todolist.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
