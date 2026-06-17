package com.mitzune.api.features.user.v1.service.impl;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.features.auth.entity.UserIdentity;
import com.mitzune.api.features.auth.repository.UserIdentityRepository;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.exception.UserException;
import com.mitzune.api.features.user.repository.UserRepository;
import com.mitzune.api.features.user.v1.dto.UserDto;
import com.mitzune.api.features.user.v1.enums.UserRole;
import com.mitzune.api.features.user.v1.mapper.UserMapper;
import com.mitzune.api.features.user.v1.service.UserService;
import com.mitzune.api.features.user.v1.service.UsernameGenerator;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserIdentityRepository userIdentityRepository;
  private final UsernameGenerator usernameGenerator;

  @Override
  public UserDto createNewUser(
    AuthProvider authProvider,
    FirebaseToken firebaseToken
  ) {
    Instant now = Instant.now();
    UserIdentity userIdentity = new UserIdentity();
    userIdentity.setAuthProvider(authProvider);
    userIdentity.setProviderId(firebaseToken.getUid());

    // Create user
    User newUser = new User();
    newUser.setUsername(usernameGenerator.generate());
    newUser.setDisplayName(firebaseToken.getName());
    newUser.setEmail(firebaseToken.getEmail());
    newUser.setUserRole(UserRole.EMPLOYEE);
    newUser.setCreatedAt(now);
    newUser.setUpdatedAt(now);

    User savedUser = userRepository.save(newUser);

    // Save to identities
    userIdentity.setUser(savedUser);
    userIdentityRepository.save(userIdentity);

    return userMapper.toDto(savedUser);
  }

  @Override
  public UserDto getCurrentUser() {
    Authentication auth =
      SecurityContextHolder.getContext().getAuthentication();

    UUID userId = UUID.fromString(auth.getName());

    // fetch user
    return userRepository
      .findById(userId)
      .map(userMapper::toDto)
      .orElseThrow(UserException::userNotFound);
  }

  @Override
  public UserDto findByUsername(String username) {
    return userRepository
      .findByUsername(username)
      .map(userMapper::toDto)
      .orElseThrow(UserException::usernameNotFound);
  }
}
