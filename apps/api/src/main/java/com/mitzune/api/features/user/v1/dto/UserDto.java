package com.mitzune.api.features.user.v1.dto;

import com.mitzune.api.features.user.v1.enums.UserRole;
import java.time.Instant;
import java.util.UUID;

public record UserDto(
  UUID id,
  String nickname,
  String email,
  UserRole userRole,
  Instant createdAt,
  Instant updatedAt
) {}
