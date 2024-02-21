package com.example.angular.springbootangularkubernetes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenException extends Exception {

  public TokenException(String message) {
    super(message);
  }
  
  public TokenException(String message, Throwable cause) {
    super(message, cause);
  }
}
