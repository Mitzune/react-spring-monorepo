package com.mitzune.api.core.util;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  @Value("${app.security.cookie-secure}")
  private boolean isCookieSecure;

  public void attachRefreshToken(
    HttpServletResponse response,
    String rawToken
  ) {
    ResponseCookie cookie = ResponseCookie.from("refresh_token", rawToken)
      .httpOnly(true)
      .secure(isCookieSecure)
      .path("/api/auth/refresh ")
      .maxAge(Duration.ofDays(5))
      .sameSite("Strict")
      .build();

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }
}
