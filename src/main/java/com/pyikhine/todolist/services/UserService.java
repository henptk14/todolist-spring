package com.pyikhine.todolist.services;

import com.pyikhine.todolist.entities.User;
import com.pyikhine.todolist.exceptions.EmailAlreadyExistsException;
import com.pyikhine.todolist.exceptions.UsernameAlreadyExistsException;
import com.pyikhine.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username " + newUser.getUsername() + " already exists");
        }
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email " + newUser.getEmail() + " already exists");
        }

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setConfirmPassword("");

        return userRepository.save(newUser);
    }
}
