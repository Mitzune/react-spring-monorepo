package com.mitzune.api.v1.user.mapper;

import com.mitzune.api.v1.user.dto.UserDto;
import com.mitzune.api.v1.user.entity.User;

public interface UserMapper {
  User fromDto(UserDto dto);

  UserDto toDto(User user);
}
