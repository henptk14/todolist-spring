package com.pyikhine.todolist.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TodoTitleExceptionResponse {
    private String todoTitle;

    public TodoTitleExceptionResponse(String s) {
        this.todoTitle = s;
    }
}
