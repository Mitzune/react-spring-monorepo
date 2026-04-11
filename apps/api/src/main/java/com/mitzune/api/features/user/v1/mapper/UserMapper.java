package com.mitzune.api.features.user.v1.mapper;

import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.v1.dto.UserDto;

public interface UserMapper {
  User fromDto(UserDto dto);

  UserDto toDto(User user);
}
