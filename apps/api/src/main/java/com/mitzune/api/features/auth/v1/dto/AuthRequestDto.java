package com.mitzune.api.features.auth.v1.dto;

import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
  @NotNull(message = "Token is required") String token,
  AuthProvider authProvider
) {}
