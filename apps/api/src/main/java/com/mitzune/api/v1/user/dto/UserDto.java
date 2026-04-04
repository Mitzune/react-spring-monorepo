package com.mitzune.api.v1.user.dto;

import com.mitzune.api.v1.user.enums.UserRole;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
  UUID id,
  String nickname,
  String email,
  UserRole userRole,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
