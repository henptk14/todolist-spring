package com.pyikhine.todolist.repository;

import com.pyikhine.todolist.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Iterable<Todo> findAllByStatusNotLike(String status);

    Iterable<Todo> findAllByUsername(String username);

    Optional<Todo> findByIdAndUsername(Long id, String username);
}
