package com.mitzune.api.core.exception;

import com.mitzune.api.core.exception.impl.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Catches all AppException subclasses (UserException, OrderException, etc.)
  @ExceptionHandler(AppException.class)
  public ResponseEntity<ErrorResponseBody> handleAppException(AppException ex) {
    return ResponseEntity.status(ex.getStatus()).body(
      ErrorResponseBody.from(ex)
    );
  }

  // Fallback for unexpected errors
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseBody> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
      ErrorResponseBody.generic()
    );
  }
}
