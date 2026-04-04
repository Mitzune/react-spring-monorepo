package com.mitzune.api.v1.auth.service.impl;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.v1.auth.dto.AuthRequestDto;
import com.mitzune.api.v1.auth.entity.UserIdentity;
import com.mitzune.api.v1.auth.enums.AuthProvider;
import com.mitzune.api.v1.auth.repository.UserIdentityRepository;
import com.mitzune.api.v1.auth.service.AuthService;
import com.mitzune.api.v1.user.dto.UserDto;
import com.mitzune.api.v1.user.mapper.UserMapper;
import com.mitzune.api.v1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserIdentityRepository userIdentityRepository;
  private final UserMapper userMapper;
  private final UserService userService;

  public FirebaseToken getUidFromToken(String idToken)
    throws FirebaseException {
    FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(
      idToken
    );

    return firebaseToken;
  }

  @Override
  public UserDto syncUser(AuthRequestDto authRequestDto) {
    try {
      FirebaseToken firebaseToken = getUidFromToken(authRequestDto.token());
      String uid = firebaseToken.getUid();

      return userIdentityRepository
        .findByAuthProviderAndProviderId(AuthProvider.MICROSOFT, uid)
        .map(UserIdentity::getUser)
        .map(userMapper::toDto)
        .orElseGet(() ->
          userService.createNewUser(authRequestDto, firebaseToken)
        );
    } catch (FirebaseException e) {
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "Invalid token given"
      );
    }
  }
}
