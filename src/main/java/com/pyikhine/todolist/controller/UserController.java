package com.pyikhine.todolist.controller;

import com.pyikhine.todolist.entities.User;
import com.pyikhine.todolist.payload.JwtLoginSuccessResponse;
import com.pyikhine.todolist.payload.LoginRequest;
import com.pyikhine.todolist.security.JwtTokenProvider;
import com.pyikhine.todolist.security.SecurityConstants;
import com.pyikhine.todolist.services.MapValidationErrorService;
import com.pyikhine.todolist.services.UserService;
import com.pyikhine.todolist.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Environment environment;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // Validate password match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwtToken));
    }

    // delete this later
    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {
        // vcap.services.mysql.credentials.host
        String jdbcUrl = environment.getProperty("vcap.services.todolist-db.credentials.jdbcUrl");
        String vcap = environment.getProperty("VCAP_SERVICES");
        String s = "";
        if (jdbcUrl != null) {
            s += "***jdbcUrl: " + jdbcUrl + " ---- ";
        }
        if (vcap != null) {
            s += "\n***vcap: " + vcap;
        }
        return ResponseEntity.ok(s.isEmpty() ? "empty" : s);
    }
}
