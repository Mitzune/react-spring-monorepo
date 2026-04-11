package com.mitzune.api.core.exception;

public record ErrorResponseBody(String code, String message, int status) {
  public static ErrorResponseBody from(BaseErrorResponse err) {
    return new ErrorResponseBody(
      err.getCode(),
      err.getMessage(),
      err.getStatus()
    );
  }

  public static ErrorResponseBody generic() {
    return new ErrorResponseBody(
      "SYS_001",
      "An unexpected error occurred",
      500
    );
  }
}
