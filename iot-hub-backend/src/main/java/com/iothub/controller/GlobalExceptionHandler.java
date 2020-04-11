package com.iothub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iothub.exceptions.MissingResourceException;
import com.iothub.exceptions.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<?> handleUnauthorizedException() {
    return ResponseEntity.badRequest().build();
  }

  @ExceptionHandler(MissingResourceException.class)
  public ResponseEntity<?> handleMissingResourceException(MissingResourceException ex) {
    log.info(ex.getMessage());
    return ResponseEntity.notFound().build();
  }
}
