package com.pyikhine.todolist.validator;

import com.pyikhine.todolist.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.getFullName().length() < 2 || user.getFullName().length() > 40) {
            errors.rejectValue("fullName", "Length", "Full name must be between 2 to 40 letters");
        }
        if (user.getUsername().length() < 4 || user.getUsername().length() > 15) {
            errors.rejectValue("username", "Length", "Username must be between 4 to 15 letters");
        }
        if (user.getEmail().length() > 50) {
            errors.rejectValue("email", "length", "Email must be less than 50 characters");
        }
        if (!user.getEmail().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")){
            errors.rejectValue("email", "Format", "Invalid email format");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "Length", "Password must be at least 6 characters");
        }
    }
}
