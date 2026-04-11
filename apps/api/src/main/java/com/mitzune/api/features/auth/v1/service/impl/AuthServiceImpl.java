package com.mitzune.api.features.auth.v1.service.impl;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.features.auth.entity.RefreshTokenSession;
import com.mitzune.api.features.auth.entity.UserIdentity;
import com.mitzune.api.features.auth.exception.AuthException;
import com.mitzune.api.features.auth.repository.RefreshTokenSessionRepository;
import com.mitzune.api.features.auth.repository.UserIdentityRepository;
import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthResponseDto;
import com.mitzune.api.features.auth.v1.service.AuthService;
import com.mitzune.api.features.auth.v1.service.DeviceService;
import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.v1.dto.UserDto;
import com.mitzune.api.features.user.v1.mapper.UserMapper;
import com.mitzune.api.features.user.v1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserIdentityRepository userIdentityRepository;
  private final RefreshTokenSessionRepository refreshTokenSessionRepository;
  private final UserMapper userMapper;
  private final UserService userService;
  private final JwtEncoder jwtEncoder;
  private final DeviceService deviceService;

  @Value("${app.security.cookie-secure}")
  private boolean isCookieSecure;

  private FirebaseToken getUidFromToken(String idToken)
    throws FirebaseException {
    FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(
      idToken
    );

    return firebaseToken;
  }

  private String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    String scope = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(5, ChronoUnit.MINUTES))
      .subject(authentication.getName())
      .claim("scope", scope)
      .build();

    return this.jwtEncoder.encode(
      JwtEncoderParameters.from(claims)
    ).getTokenValue();
  }

  private String issueToken(UUID id, String role) {
    List<SimpleGrantedAuthority> authorities = List.of(
      new SimpleGrantedAuthority(role)
    );

    Authentication authentication = new UsernamePasswordAuthenticationToken(
      id,
      null,
      authorities
    );

    return this.generateToken(authentication);
  }

  private String getRealIp(HttpServletRequest request) {
    String xff = request.getHeader("X-Forwarded-For");
    if (xff != null && !xff.isBlank()) {
      return xff.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }

  private String createRefreshToken(User user, HttpServletRequest request) {
    String rawRefreshToken = UUID.randomUUID().toString();
    Instant now = Instant.now();
    RefreshTokenSession refreshTokenSession = new RefreshTokenSession();

    // Device logic
    String ua = request.getHeader("User-Agent");
    refreshTokenSession.setDeviceInfo(deviceService.parseDevice(ua));

    // Ip address
    refreshTokenSession.setIpAddress(getRealIp(request));

    refreshTokenSession.setUser(user);
    refreshTokenSession.setCreatedAt(now);
    refreshTokenSession.setTokenHash(rawRefreshToken);
    refreshTokenSession.setExpiresAt(now.plus(5, ChronoUnit.DAYS));
    refreshTokenSessionRepository.save(refreshTokenSession);

    return rawRefreshToken;
  }

  private void attachRefreshToken(
    HttpServletResponse response,
    String rawToken
  ) {
    ResponseCookie cookie = ResponseCookie.from("refresh_token", rawToken)
      .httpOnly(true)
      .secure(isCookieSecure)
      .path("/api/auth/refresh")
      .maxAge(Duration.ofDays(5))
      .sameSite("Strict")
      .build();

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  @Transactional
  @Override
  public AuthResponseDto syncUser(
    AuthRequestDto authRequestDto,
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse
  ) {
    try {
      FirebaseToken firebaseToken = getUidFromToken(authRequestDto.token());
      String uid = firebaseToken.getUid();

      User user = userIdentityRepository
        .findByAuthProviderAndProviderId(authRequestDto.authProvider(), uid)
        .map(UserIdentity::getUser)
        .orElseGet(() -> {
          UserDto newUser = userService.createNewUser(
            authRequestDto,
            firebaseToken
          );

          return userMapper.fromDto(newUser);
        });

      String refreshToken = createRefreshToken(user, httpServletRequest);
      attachRefreshToken(httpServletResponse, refreshToken);

      return new AuthResponseDto(
        userMapper.toDto(user),
        this.issueToken(user.getId(), user.getUserRole().name())
      );
    } catch (FirebaseException e) {
      throw AuthException.ssoTokenInvalid();
    }
  }

  @Transactional
  @Override
  public AuthResponseDto refreshTokens(String refreshToken) {
    if (refreshToken.isEmpty()) {
      throw AuthException.noRefreshToken();
    }

    RefreshTokenSession refreshTokenSession = refreshTokenSessionRepository
      .findByTokenHash(refreshToken)
      .orElseThrow(() -> AuthException.refreshTokenNotFound());

    if (refreshTokenSession.getRevokedAt() != null) {
      throw AuthException.refreshTokenRevoked();
    }

    if (refreshTokenSession.getExpiresAt().isBefore(Instant.now())) {
      refreshTokenSessionRepository.delete(refreshTokenSession);
      throw AuthException.refreshTokenExpired();
    }

    User user = refreshTokenSession.getUser();

    return new AuthResponseDto(
      userMapper.toDto(user),
      this.issueToken(user.getId(), user.getUserRole().name())
    );
  }
}
