package com.pyikhine.todolist.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class JwtLoginSuccessResponse {
    private boolean success;
    private String token;


}
