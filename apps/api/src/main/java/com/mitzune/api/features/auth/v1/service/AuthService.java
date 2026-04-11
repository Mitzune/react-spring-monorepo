package com.mitzune.api.features.auth.v1.service;

import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
  AuthResponseDto syncUser(
    AuthRequestDto authRequestDto,
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse
  );

  AuthResponseDto refreshTokens(String refreshToken);
}
