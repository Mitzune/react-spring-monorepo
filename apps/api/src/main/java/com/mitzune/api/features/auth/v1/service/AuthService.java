package com.mitzune.api.features.auth.v1.service;

import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthSyncResult;
import com.mitzune.api.features.auth.v1.dto.AuthTokenResponse;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;

public interface AuthService {
  AuthSyncResult syncUser(
    AuthRequestDto authRequestDto,
    AuthProvider authProvider,
    String ua,
    String ip
  );

  AuthTokenResponse refreshTokens(String refreshToken);
}
