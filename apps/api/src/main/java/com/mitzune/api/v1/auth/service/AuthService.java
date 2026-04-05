package com.mitzune.api.v1.auth.service;

import com.mitzune.api.v1.auth.dto.AuthRequestDto;
import com.mitzune.api.v1.user.dto.UserResponseDto;

public interface AuthService {
  UserResponseDto syncUser(AuthRequestDto authRequestDto);
}
