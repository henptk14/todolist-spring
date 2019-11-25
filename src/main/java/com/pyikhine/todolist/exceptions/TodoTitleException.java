package com.pyikhine.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TodoTitleException extends RuntimeException {
    public TodoTitleException(String arg0) {
        super(arg0);
    }
}
