package com.pyikhine.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String args0) {
        super(args0);
    }
}
