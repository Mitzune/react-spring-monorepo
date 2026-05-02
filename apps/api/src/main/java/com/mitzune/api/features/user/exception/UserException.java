package com.mitzune.api.features.user.exception;

import com.mitzune.api.core.exception.impl.AppException;
import org.springframework.http.HttpStatus;

public class UserException extends AppException {

  private UserException(HttpStatus status, String code, String message) {
    super(status, code, message);
  }

  public static UserException userNotFound() {
    return new UserException(HttpStatus.NOT_FOUND, "USER_001", "No user found");
  }
}
