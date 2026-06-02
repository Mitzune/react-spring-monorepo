package com.mitzune.api.features.auth.v1.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.core.util.FirebaseUtil;
import com.mitzune.api.features.auth.entity.RefreshTokenSession;
import com.mitzune.api.features.auth.exception.AuthException;
import com.mitzune.api.features.auth.repository.RefreshTokenSessionRepository;
import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthSyncResult;
import com.mitzune.api.features.auth.v1.dto.RefreshResponse;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.auth.v1.service.DeviceService;
import com.mitzune.api.features.auth.v1.service.UserIdentityService;
import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.v1.dto.UserDto;
import com.mitzune.api.features.user.v1.enums.UserRole;
import com.mitzune.api.features.user.v1.mapper.UserMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit test")
class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authService;

  @Mock
  private UserIdentityService userIdentityService;

  @Mock
  private UserMapper userMapper;

  @Mock
  private AuthRequestDto authRequestDto;

  @Mock
  private DeviceService deviceService;

  @Mock
  private JwtEncoder jwtEncoder;

  @Mock
  private FirebaseUtil firebaseUtil;

  @Mock
  private RefreshTokenSessionRepository refreshTokenSessionRepository;

  @Mock
  private AuthSyncResult authSyncResult;

  private User mockUser;

  private UserDto mockUserDto;

  private AuthProvider authProvider;

  private static String TOKEN = "valid_token";
  private static String TOKEN_HASH = DigestUtils.sha256Hex(TOKEN);
  private static String USER_AGENT = "Mozilla/5.0";
  private static String IP = "127.0.0.1";

  @BeforeEach
  void setup() {
    this.authRequestDto = new AuthRequestDto("sample_hash_token");

    this.mockUser = User.builder()
      .id(UUID.fromString("57179621-b3e0-4858-841b-ca39c7dddbe4"))
      .userRole(UserRole.EMPLOYEE)
      .build();
    this.mockUserDto = mock(UserDto.class);

    authProvider = AuthProvider.GOOGLE;
  }

  @Nested
  class SyncUserTests {

    @Test
    @DisplayName("Sync user created successfully")
    void should_create_user_successfully() {
      AuthProvider authProvider = AuthProvider.GOOGLE;

      AuthRequestDto authRequestDto = new AuthRequestDto(TOKEN);
      FirebaseToken mockFirebaseToken = mock(FirebaseToken.class);
      Jwt mockJwt = Jwt.withTokenValue("mock-token")
        .header("alg", "RS256")
        .claim("sub", "someUserId")
        .build();

      when(firebaseUtil.verifyIdToken(TOKEN)).thenReturn(mockFirebaseToken);
      when(
        userIdentityService.getOrCreateUser(
          AuthProvider.GOOGLE,
          mockFirebaseToken
        )
      ).thenReturn(mockUser);
      when(userMapper.toDto(mockUser)).thenReturn(mockUserDto);
      when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(
        mockJwt
      );

      AuthSyncResult result = authService.syncUser(
        authRequestDto,
        authProvider,
        USER_AGENT,
        IP
      );

      assertNotNull(result);
      assertNotNull(result.refreshToken());
      verify(firebaseUtil).verifyIdToken(TOKEN);
      verify(userIdentityService).getOrCreateUser(
        AuthProvider.GOOGLE,
        mockFirebaseToken
      );
      verify(refreshTokenSessionRepository).save(
        any(RefreshTokenSession.class)
      );
    }

    @Test
    @DisplayName("Invalid SSO Token")
    void invalid_sso_token_error() {
      AuthRequestDto authRequestDto = new AuthRequestDto(TOKEN);

      when(firebaseUtil.verifyIdToken(TOKEN)).thenThrow(
        AuthException.ssoTokenInvalid()
      );

      AuthException authException = assertThrows(AuthException.class, () ->
        authService.syncUser(authRequestDto, authProvider, USER_AGENT, IP)
      );

      assertEquals("Invalid SSO token", authException.getMessage());
      verifyNoInteractions(userIdentityService, userMapper);
    }
  }

  @Nested
  class RotateRefreshTokenTest {

    @Test
    @DisplayName("Rotated refresh token successfully")
    void rotate_refresh_token_successfully() {
      RefreshTokenSession validSession = RefreshTokenSession.builder()
        .user(mockUser)
        .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
        .build();
      Jwt mockJwt = Jwt.withTokenValue("mock-token")
        .header("alg", "RS256")
        .claim("sub", "someUserId")
        .build();

      when(
        refreshTokenSessionRepository.findByTokenHash(TOKEN_HASH)
      ).thenReturn(Optional.of(validSession));
      when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(
        mockJwt
      );

      RefreshResponse result = authService.rotateRefreshToken(
        TOKEN,
        USER_AGENT,
        IP
      );

      assertNotNull(result);
      assertNotNull(result.accessToken());
      assertNotNull(result.refreshToken());

      verify(refreshTokenSessionRepository).findByTokenHash(TOKEN_HASH);
      verify(refreshTokenSessionRepository).saveAndFlush(
        any(RefreshTokenSession.class)
      );
    }

    @Test
    @DisplayName("Expired refresh token")
    void expired_refresh_token() {
      Instant now = Instant.now();

      RefreshTokenSession expiredSession = RefreshTokenSession.builder()
        .user(mockUser)
        .expiresAt(now.minusSeconds(1))
        .build();

      when(
        refreshTokenSessionRepository.findByTokenHash(TOKEN_HASH)
      ).thenReturn(Optional.of(expiredSession));

      AuthException authException = assertThrows(AuthException.class, () ->
        authService.rotateRefreshToken(TOKEN, USER_AGENT, IP)
      );

      assertEquals("Refresh token has expired", authException.getMessage());

      verify(refreshTokenSessionRepository).findByTokenHash(TOKEN_HASH);
      verify(refreshTokenSessionRepository, never()).saveAndFlush(
        any(RefreshTokenSession.class)
      );
    }

    @Test
    @DisplayName("Revoked refresh token")
    void revoked_refresh_token() {
      Instant now = Instant.now();

      RefreshTokenSession expiredSession = RefreshTokenSession.builder()
        .user(mockUser)
        .expiresAt(now.plus(1, ChronoUnit.DAYS))
        .revokedAt(now)
        .build();

      when(
        refreshTokenSessionRepository.findByTokenHash(TOKEN_HASH)
      ).thenReturn(Optional.of(expiredSession));

      AuthException authException = assertThrows(AuthException.class, () ->
        authService.rotateRefreshToken(TOKEN, USER_AGENT, IP)
      );

      assertEquals("Refresh token is revoked", authException.getMessage());

      verify(refreshTokenSessionRepository).findByTokenHash(TOKEN_HASH);
      verify(refreshTokenSessionRepository, never()).saveAndFlush(
        any(RefreshTokenSession.class)
      );
    }

    @Test
    @DisplayName("Refresh token not found")
    void not_found_refresh_token() {
      AuthException authException = assertThrows(AuthException.class, () ->
        authService.rotateRefreshToken(TOKEN, USER_AGENT, IP)
      );

      assertEquals("Refresh token not found", authException.getMessage());

      verify(refreshTokenSessionRepository).findByTokenHash(anyString());
      verify(refreshTokenSessionRepository, never()).saveAndFlush(
        any(RefreshTokenSession.class)
      );
    }

    @Test
    @DisplayName("Refresh token was reused")
    void used_rotated_refresh_token() {
      Instant now = Instant.now();

      RefreshTokenSession expiredSession = RefreshTokenSession.builder()
        .user(mockUser)
        .expiresAt(now.plus(1, ChronoUnit.DAYS))
        .familyId(TOKEN_HASH) // Random generated family id
        .rotatedAt(now)
        .build();

      when(
        refreshTokenSessionRepository.findByTokenHash(TOKEN_HASH)
      ).thenReturn(Optional.of(expiredSession));

      AuthException authException = assertThrows(AuthException.class, () ->
        authService.rotateRefreshToken(TOKEN, USER_AGENT, IP)
      );

      assertEquals("Refresh token was reused", authException.getMessage());

      verify(refreshTokenSessionRepository).findByTokenHash(anyString());
      verify(refreshTokenSessionRepository, never()).save(
        any(RefreshTokenSession.class)
      );
    }
  }
}
