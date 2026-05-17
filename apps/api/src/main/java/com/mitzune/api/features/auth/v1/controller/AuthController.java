package com.mitzune.api.features.auth.v1.controller;

import com.mitzune.api.core.util.CookieUtil;
import com.mitzune.api.core.util.WebUtil;
import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthResponseDto;
import com.mitzune.api.features.auth.v1.dto.AuthSyncResult;
import com.mitzune.api.features.auth.v1.dto.AuthTokenResponse;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.auth.v1.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final CookieUtil cookieUtil;
  private final WebUtil webUtil;

  @PostMapping("/{provider}")
  public ResponseEntity<AuthResponseDto> syncAccount(
    @PathVariable("provider") AuthProvider authProvider,
    @Valid @RequestBody AuthRequestDto authRequestDto,
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse
  ) {
    String ua = httpServletRequest.getHeader("User-Agent");
    String ip = webUtil.getRealIp(httpServletRequest);

    AuthSyncResult authSyncResult = authService.syncUser(
      authRequestDto,
      authProvider,
      ua,
      ip
    );

    cookieUtil.attachRefreshToken(
      httpServletResponse,
      authSyncResult.refreshToken()
    );

    return ResponseEntity.ok(authSyncResult.authResponseDto());
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthTokenResponse> refreshTokens(
    @CookieValue(name = "refresh_token") String refreshToken
  ) {
    return ResponseEntity.ok(authService.refreshTokens(refreshToken));
  }
}
