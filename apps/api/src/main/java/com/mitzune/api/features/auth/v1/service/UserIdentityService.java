package com.mitzune.api.features.auth.v1.service;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.user.entity.User;

public interface UserIdentityService {
  User getOrCreateUser(AuthProvider authProvider, FirebaseToken firebaseToken);
}
