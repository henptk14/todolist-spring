package com.pyikhine.todolist.repository;

import com.pyikhine.todolist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1 or u.email = ?1")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    Optional<User> findById(Long id);
}
