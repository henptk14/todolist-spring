package com.pyikhine.todolist.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {
    public static final Logger logger = LogManager.getLogger(MapValidationErrorService.class);

    public ResponseEntity<?> MapValidationService(BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            logger.info(errorMap.entrySet().toString());

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
