package com.pyikhine.todolist.entities.integritycheck;

import com.pyikhine.todolist.entities.Todo;
import org.springframework.util.StringUtils;

public class TodoDataIntegrity {
    public static final String TITLE_ERROR = "Todo Title cannot be blank or null when updating";
    public static final String USER_ERROR = "Todo's User cannot be null when updating.";
    public static final String USERNAME_ERROR = "Todo's Username cannot be blank or null when updating.";

    public static String updateCheck(Todo todo) {
        if (StringUtils.isEmpty(todo.getTodoTitle())) {
            return TITLE_ERROR;
        }
        if (todo.getUser() == null) {
            return USER_ERROR;
        }
        if (StringUtils.isEmpty(todo.getUsername())) {
            return USERNAME_ERROR;
        }
        return "";
    }
}
