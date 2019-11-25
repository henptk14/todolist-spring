package com.pyikhine.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TodoDataIntegrityBrokenException extends RuntimeException {
    public TodoDataIntegrityBrokenException(String args0) {
        super(args0);
    }
}
