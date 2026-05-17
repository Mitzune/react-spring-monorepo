package com.mitzune.api.features.auth.v1.service.impl;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.features.auth.entity.UserIdentity;
import com.mitzune.api.features.auth.repository.UserIdentityRepository;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.auth.v1.service.UserIdentityService;
import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.v1.dto.UserDto;
import com.mitzune.api.features.user.v1.mapper.UserMapper;
import com.mitzune.api.features.user.v1.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserIdentityServiceImpl implements UserIdentityService {

  private final UserIdentityRepository userIdentityRepository;
  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public User getOrCreateUser(
    AuthProvider authProvider,
    FirebaseToken firebaseToken
  ) {
    return userIdentityRepository
      .findByAuthProviderAndProviderId(authProvider, firebaseToken.getUid())
      .map(UserIdentity::getUser)
      .orElseGet(() -> {
        UserDto newUser = userService.createNewUser(
          authProvider,
          firebaseToken
        );

        return userMapper.fromDto(newUser);
      });
  }
}
