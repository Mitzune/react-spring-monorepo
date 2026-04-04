package com.mitzune.api.v1.user.service;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.v1.auth.dto.AuthRequestDto;
import com.mitzune.api.v1.user.dto.UserDto;

public interface UserService {
  UserDto createNewUser(
    AuthRequestDto authRequestDto,
    FirebaseToken firebaseToken
  );
}
