package com.mitzune.api.features.auth.v1.controller;

import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthResponseDto;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.auth.v1.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/Microsoft")
  public ResponseEntity<AuthResponseDto> createAccountWithMicrosoft(
    @Valid @RequestBody AuthRequestDto authRequestDto,
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse
  ) {
    AuthRequestDto userRequest = new AuthRequestDto(
      authRequestDto.token(),
      AuthProvider.MICROSOFT
    );
    return ResponseEntity.ok(
      authService.syncUser(userRequest, httpServletRequest, httpServletResponse)
    );
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponseDto> refreshTokens(
    @CookieValue(name = "refresh_token") String refreshToken
  ) {
    return ResponseEntity.ok(authService.refreshTokens(refreshToken));
  }
}
