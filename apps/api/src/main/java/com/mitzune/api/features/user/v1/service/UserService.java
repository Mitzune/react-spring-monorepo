package com.mitzune.api.features.user.v1.service;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.user.v1.dto.UserDto;

public interface UserService {
  UserDto createNewUser(
    AuthRequestDto authRequestDto,
    FirebaseToken firebaseToken
  );
}
