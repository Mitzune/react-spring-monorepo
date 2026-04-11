package com.mitzune.api.core.exception.impl;

import com.mitzune.api.core.exception.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AppException
  extends ResponseStatusException
  implements BaseErrorResponse
{

  private final String code;

  protected AppException(HttpStatus status, String code, String message) {
    super(status, message);
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return getReason();
  }

  @Override
  public int getStatus() {
    return getStatusCode().value();
  }
}
