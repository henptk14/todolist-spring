package com.pyikhine.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<TodoTitleException> handleTodoTitleException(TodoTitleException ex, WebRequest request) {
        TodoTitleExceptionResponse todoTitleExceptionResponse = new TodoTitleExceptionResponse(ex.getMessage());

        return new ResponseEntity(todoTitleExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<TodoTitleException> handleTodoNotFoundException(TodoNotFoundException ex, WebRequest request) {
        TodoNotFoundExceptionResponse todoNotFoundExceptionResponse = new TodoNotFoundExceptionResponse(ex.getMessage());

        return new ResponseEntity(todoNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<TodoTitleException> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        TaskNotFoundExceptionResponse taskNotFoundExceptionResponse = new TaskNotFoundExceptionResponse(ex.getMessage());

        return new ResponseEntity(taskNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<UsernameAlreadyExistsException> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        UsernameAlreadyExistsResponse usernameAlreadyExistsResponse = new UsernameAlreadyExistsResponse(ex.getMessage());

        return new ResponseEntity(usernameAlreadyExistsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<EmailAlreadyExistsException> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        EmailAlreadyExistsResponse emailAlreadyExistsResponse = new EmailAlreadyExistsResponse(ex.getMessage());

        return new ResponseEntity(emailAlreadyExistsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<TodoDataIntegrityBrokenException> handleTodoDataIntegrityBrokenException(TodoDataIntegrityBrokenException ex, WebRequest request) {
        TodoDataIntegrityBrokenExceptionResponse todoDataIntegrityBrokenExceptionResponse = new TodoDataIntegrityBrokenExceptionResponse(ex.getMessage());

        return new ResponseEntity(todoDataIntegrityBrokenExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
