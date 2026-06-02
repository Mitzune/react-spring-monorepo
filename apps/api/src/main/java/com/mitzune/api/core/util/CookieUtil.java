package com.mitzune.api.core.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  @Value("${app.security.cookie-secure}")
  private boolean isCookieSecure;

  @Value("${auth.refresh-token.ttl-days}")
  private int tokenTtlDays;

  public static final String REFRESH_COOKIE_NAME = "refresh_token";
  public static final String REFRESH_COOKIE_PATH = "/api/v1/auth";

  public void attachRefreshToken(
    HttpServletResponse response,
    String rawToken
  ) {
    ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE_NAME, rawToken)
      .httpOnly(true)
      .secure(isCookieSecure)
      .path(REFRESH_COOKIE_PATH)
      .maxAge(Duration.ofDays(tokenTtlDays))
      .sameSite("Strict")
      .build();

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public void clearRefreshCookie(HttpServletResponse response) {
    ResponseCookie expiredCookie = ResponseCookie.from(REFRESH_COOKIE_NAME, "")
      .httpOnly(true)
      .secure(isCookieSecure)
      .path(REFRESH_COOKIE_PATH)
      .maxAge(0)
      .sameSite("Strict")
      .build();

    response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
  }

  public String extractCookie(HttpServletRequest request, String name) {
    if (request.getCookies() == null) return null;
    return Arrays.stream(request.getCookies())
      .filter(c -> name.equals(c.getName()))
      .map(Cookie::getValue)
      .findFirst()
      .orElse(null);
  }
}
