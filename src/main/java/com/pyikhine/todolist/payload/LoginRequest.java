package com.pyikhine.todolist.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginRequest {

    @NotBlank(message = "Username or Email cannot be blank")
    private String usernameOrEmail;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
