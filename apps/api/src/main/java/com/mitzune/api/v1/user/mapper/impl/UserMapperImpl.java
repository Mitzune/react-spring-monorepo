package com.mitzune.api.v1.user.mapper.impl;

import com.mitzune.api.v1.user.dto.UserDto;
import com.mitzune.api.v1.user.entity.User;
import com.mitzune.api.v1.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public User fromDto(UserDto dto) {
    return User.builder()
      .displayName(dto.nickname())
      .email(dto.email())
      .build();
  }

  @Override
  public UserDto toDto(User user) {
    return new UserDto(
      user.getId(),
      user.getDisplayName(),
      user.getEmail(),
      user.getUserRole(),
      user.getCreatedAt(),
      user.getUpdatedAt()
    );
  }
}
