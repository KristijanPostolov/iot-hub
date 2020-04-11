package com.iothub.exceptions;

public class MissingResourceException extends RuntimeException {
  public MissingResourceException(String message) {
    super(message);
  }
}
