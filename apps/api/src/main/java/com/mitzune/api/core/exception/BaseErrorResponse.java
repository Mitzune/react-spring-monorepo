package com.mitzune.api.core.exception;

public interface BaseErrorResponse {
  String getCode();
  String getMessage();
  int getStatus();
}
