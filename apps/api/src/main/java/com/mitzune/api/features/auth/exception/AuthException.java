package com.mitzune.api.features.auth.exception;

import com.mitzune.api.core.exception.impl.AppException;
import org.springframework.http.HttpStatus;

public class AuthException extends AppException {

  private AuthException(HttpStatus status, String code, String message) {
    super(status, code, message);
  }

  public static AuthException ssoTokenInvalid() {
    return new AuthException(
      HttpStatus.NOT_FOUND,
      "AUTH_001",
      "Invalid SSO token"
    );
  }

  public static AuthException noRefreshToken() {
    return new AuthException(
      HttpStatus.UNAUTHORIZED,
      "AUTH_002",
      "Refresh token not sent"
    );
  }

  public static AuthException refreshTokenNotFound() {
    return new AuthException(
      HttpStatus.UNAUTHORIZED,
      "AUTH_003",
      "Refresh token not found"
    );
  }

  public static AuthException refreshTokenRevoked() {
    return new AuthException(
      HttpStatus.UNAUTHORIZED,
      "AUTH_004",
      "Refresh token is revoked"
    );
  }

  public static AuthException refreshTokenExpired() {
    return new AuthException(
      HttpStatus.UNAUTHORIZED,
      "AUTH_005",
      "Refresh token has expired"
    );
  }
}
