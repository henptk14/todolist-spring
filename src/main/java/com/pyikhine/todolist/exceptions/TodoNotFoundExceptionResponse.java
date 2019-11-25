package com.pyikhine.todolist.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TodoNotFoundExceptionResponse {
    private String todoId;

    public TodoNotFoundExceptionResponse(String s) {
        this.todoId = s;
    }
}
