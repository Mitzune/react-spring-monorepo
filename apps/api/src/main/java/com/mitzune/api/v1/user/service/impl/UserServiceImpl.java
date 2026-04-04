package com.mitzune.api.v1.user.service.impl;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.v1.auth.dto.AuthRequestDto;
import com.mitzune.api.v1.auth.entity.UserIdentity;
import com.mitzune.api.v1.auth.repository.UserIdentityRepository;
import com.mitzune.api.v1.user.dto.UserDto;
import com.mitzune.api.v1.user.entity.User;
import com.mitzune.api.v1.user.enums.UserRole;
import com.mitzune.api.v1.user.mapper.UserMapper;
import com.mitzune.api.v1.user.repository.UserRepository;
import com.mitzune.api.v1.user.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserIdentityRepository userIdentityRepository;

  @Override
  public UserDto createNewUser(
    AuthRequestDto authRequestDto,
    FirebaseToken firebaseToken
  ) {
    UserIdentity userIdentity = new UserIdentity();
    userIdentity.setAuthProvider(authRequestDto.authProvider());

    userIdentity.setProviderId(firebaseToken.getUid());
    Optional<User> user = userRepository.findByEmail(firebaseToken.getEmail());

    // If user exists
    if (user.isPresent()) {
      User existingUser = user.get();
      userIdentity.setUser(existingUser);
      userIdentityRepository.save(userIdentity);

      return userMapper.toDto(existingUser);
    }

    // Create user
    User createUser = new User();
    createUser.setDisplayName(firebaseToken.getName());
    createUser.setEmail(firebaseToken.getEmail());
    createUser.setUserRole(UserRole.EMPLOYEE);

    User savedUser = userRepository.save(createUser);

    // Save to identities
    userIdentity.setUser(savedUser);
    userIdentityRepository.save(userIdentity);

    return userMapper.toDto(savedUser);
  }
}
