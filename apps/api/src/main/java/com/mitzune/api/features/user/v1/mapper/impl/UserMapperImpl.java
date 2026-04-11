package com.mitzune.api.features.user.v1.mapper.impl;

import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.v1.dto.UserDto;
import com.mitzune.api.features.user.v1.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public User fromDto(UserDto dto) {
    return User.builder()
      .id(dto.id())
      .displayName(dto.nickname())
      .userRole(dto.userRole())
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
