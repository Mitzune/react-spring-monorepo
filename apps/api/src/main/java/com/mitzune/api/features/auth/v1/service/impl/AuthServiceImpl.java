package com.mitzune.api.features.auth.v1.service.impl;

import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.core.util.FirebaseUtil;
import com.mitzune.api.features.auth.entity.RefreshTokenSession;
import com.mitzune.api.features.auth.exception.AuthException;
import com.mitzune.api.features.auth.repository.RefreshTokenSessionRepository;
import com.mitzune.api.features.auth.v1.dto.AuthRequestDto;
import com.mitzune.api.features.auth.v1.dto.AuthResponseDto;
import com.mitzune.api.features.auth.v1.dto.AuthSyncResult;
import com.mitzune.api.features.auth.v1.dto.RefreshResponse;
import com.mitzune.api.features.auth.v1.dto.RefreshTokenPair;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import com.mitzune.api.features.auth.v1.service.AuthService;
import com.mitzune.api.features.auth.v1.service.DeviceService;
import com.mitzune.api.features.auth.v1.service.UserIdentityService;
import com.mitzune.api.features.user.entity.User;
import com.mitzune.api.features.user.v1.mapper.UserMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final RefreshTokenSessionRepository refreshTokenSessionRepository;
  private final UserMapper userMapper;
  private final JwtEncoder jwtEncoder;
  private final DeviceService deviceService;
  private final UserIdentityService userIdentityService;
  private final FirebaseUtil firebaseUtil;

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

  private String issueAccessToken(UUID id, String role) {
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

  @Value("${auth.refresh-token.ttl-days}")
  private int tokenTtlDays;

  private RefreshTokenSession buildSession(
    User user,
    RefreshTokenPair pair,
    String ua,
    String ip,
    String familyId
  ) {
    Instant now = Instant.now();
    if (familyId == null || familyId.isBlank()) {
      familyId = UUID.randomUUID().toString();
    }

    RefreshTokenSession session = new RefreshTokenSession();
    session.setUser(user);
    session.setDeviceInfo(deviceService.parseDevice(ua));
    session.setCreatedIpAddress(ip);
    session.setLastIpAddressUsed(ip);
    session.setCreatedAt(now);
    session.setUpdatedAt(now);
    session.setFamilyId(familyId);
    session.setTokenHash(pair.tokenHash());
    session.setExpiresAt(now.plus(tokenTtlDays, ChronoUnit.DAYS));
    return session;
  }

  private RefreshTokenPair createRefreshToken() {
    String rawRefreshToken = UUID.randomUUID().toString();
    String hash = DigestUtils.sha256Hex(rawRefreshToken);

    return new RefreshTokenPair(rawRefreshToken, hash);
  }

  private void revokeTokenFamily(String familyId, Instant now) {
    refreshTokenSessionRepository.revokeFamily(familyId, now);
  }

  @Transactional
  @Override
  public AuthSyncResult syncUser(
    AuthRequestDto authRequestDto,
    AuthProvider authProvider,
    String ua,
    String ip
  ) {
    FirebaseToken firebaseToken = firebaseUtil.verifyIdToken(
      authRequestDto.token()
    );

    User user = userIdentityService.getOrCreateUser(
      authProvider,
      firebaseToken
    );

    RefreshTokenPair refreshTokenPair = createRefreshToken();
    RefreshTokenSession refreshTokenSession = buildSession(
      user,
      refreshTokenPair,
      ua,
      ip,
      ""
    );
    refreshTokenSessionRepository.save(refreshTokenSession);

    return new AuthSyncResult(
      new AuthResponseDto(
        userMapper.toDto(user),
        this.issueAccessToken(user.getId(), user.getUserRole().name())
      ),
      refreshTokenPair.rawToken()
    );
  }

  @Transactional(noRollbackFor = { AuthException.class })
  @Override
  public RefreshResponse rotateRefreshToken(
    String refreshToken,
    String ua,
    String ip
  ) {
    Instant now = Instant.now();
    String hash = DigestUtils.sha256Hex(refreshToken);

    RefreshTokenSession oldSession = refreshTokenSessionRepository
      .findByTokenHash(hash)
      .orElseThrow(AuthException::refreshTokenNotFound);

    if (oldSession.getRotatedAt() != null) {
      revokeTokenFamily(oldSession.getFamilyId(), now);
      throw AuthException.refreshTokenReused();
    }

    if (oldSession.getRevokedAt() != null) {
      revokeTokenFamily(oldSession.getFamilyId(), now);
      throw AuthException.refreshTokenRevoked();
    }

    if (oldSession.getExpiresAt().isBefore(now)) {
      revokeTokenFamily(oldSession.getFamilyId(), now);
      throw AuthException.refreshTokenExpired();
    }

    User user = oldSession.getUser();

    oldSession.setRevokedAt(now);
    oldSession.setUpdatedAt(now);
    oldSession.setRotatedAt(now);
    oldSession.setLastIpAddressUsed(ip);
    refreshTokenSessionRepository.saveAndFlush(oldSession);

    RefreshTokenPair refreshTokenPair = createRefreshToken();
    RefreshTokenSession refreshTokenSession = buildSession(
      user,
      refreshTokenPair,
      ua,
      ip,
      oldSession.getFamilyId()
    );
    refreshTokenSessionRepository.save(refreshTokenSession);

    return new RefreshResponse(
      this.issueAccessToken(user.getId(), user.getUserRole().name()),
      refreshTokenPair.rawToken()
    );
  }

  @Transactional
  @Override
  public void logoutUser(String refreshToken, String ip) {
    String hash = DigestUtils.sha256Hex(refreshToken);
    Instant now = Instant.now();
    RefreshTokenSession session = refreshTokenSessionRepository
      .findByTokenHash(hash)
      .orElseThrow(AuthException::refreshTokenNotFound);

    session.setUpdatedAt(now);
    session.setRevokedAt(now);
    session.setLastIpAddressUsed(ip);
    refreshTokenSessionRepository.saveAndFlush(session);

    revokeTokenFamily(session.getFamilyId(), now);
  }
}
