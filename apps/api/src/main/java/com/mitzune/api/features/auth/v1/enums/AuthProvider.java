package com.mitzune.api.features.auth.v1.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mitzune.api.features.auth.exception.AuthException;
import java.util.Arrays;

public enum AuthProvider {
  GOOGLE,
  MICROSOFT;

  @JsonCreator
  public static AuthProvider fromValue(String value) {
    return Arrays.stream(values())
      .filter(v -> v.name().equalsIgnoreCase(value))
      .findFirst()
      .orElseThrow(() -> AuthException.noSsoAvailable(value));
  }
}
