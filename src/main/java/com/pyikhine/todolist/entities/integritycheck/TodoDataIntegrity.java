package com.pyikhine.todolist.entities.integritycheck;

import com.pyikhine.todolist.entities.Todo;
import org.springframework.util.StringUtils;

public class TodoDataIntegrity {
    public static String check(Todo todo) {
        if (StringUtils.isEmpty(todo.getTodoTitle())) {
            return "Todo Title is null.";
        }
        if (todo.getUser() == null) {
            return "Todo's User is null.";
        }
        if (StringUtils.isEmpty(todo.getUsername())) {
            return "Todo's Username is null.";
        }
        return "";
    }
}
