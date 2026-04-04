package com.mitzune.api.v1.auth.dto;

import com.mitzune.api.v1.auth.enums.AuthProvider;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
  @NotNull(message = "Token is required") String token,
  AuthProvider authProvider
) {}
