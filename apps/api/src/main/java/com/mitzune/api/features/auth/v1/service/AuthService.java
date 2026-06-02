package com.mitzune.api.features.auth.v1.service;

import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthSyncResult;
import com.mitzune.api.features.auth.v1.dto.RefreshResponse;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;

public interface AuthService {
  AuthSyncResult syncUser(
    AuthRequestDto authRequestDto,
    AuthProvider authProvider,
    String ua,
    String ip
  );

  RefreshResponse rotateRefreshToken(String refreshToken, String ua, String ip);

  void logoutUser(String refreshToken, String ip);
}
