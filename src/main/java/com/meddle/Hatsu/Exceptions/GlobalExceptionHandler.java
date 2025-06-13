package com.meddle.Hatsu.Exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(EntityNotFoundException.class)
   ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception) {
      HashMap<String, Object> body = new HashMap<String, Object>();
      body.put("timestamp", LocalDateTime.now());
      body.put("status", HttpStatus.NOT_FOUND.value());
      body.put("message", exception.getMessage());

      return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
   }

   // TODO: investigate why this is returning 500
   @ExceptionHandler(DuplicateEntityException.class)
   ResponseEntity<Object> handleDuplicateEntity(DuplicateEntityException exception) {
      HashMap<String, Object> body = new HashMap<String, Object>();
      body.put("timestamp", LocalDateTime.now());
      body.put("status", HttpStatus.BAD_REQUEST.value());
      body.put("message", exception.getMessage());

      return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(InvalidCredentialsException.class)
   ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException exception) {
      HashMap<String, Object> body = new HashMap<String, Object>();
      body.put("timestamp", LocalDateTime.now());
      body.put("status", HttpStatus.BAD_REQUEST.value());
      body.put("message", exception.getMessage());

      return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(Exception.class)
   ResponseEntity<Object> handleException(Exception exception) {
      HashMap<String, Object> body = new HashMap<String, Object>();
      body.put("timestamp", LocalDateTime.now());
      body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      body.put("message", exception.getMessage());

      return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
   }

}
