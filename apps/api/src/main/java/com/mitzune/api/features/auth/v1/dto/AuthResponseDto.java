package com.mitzune.api.features.auth.v1.dto;

import com.mitzune.api.features.user.v1.dto.UserDto;

public record AuthResponseDto(UserDto user, String token) {}
