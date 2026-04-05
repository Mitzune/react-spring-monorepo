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
import com.mitzune.api.v1.user.dto.UserResponseDto;
import com.mitzune.api.v1.user.mapper.UserMapper;
import com.mitzune.api.v1.user.service.UserService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserIdentityRepository userIdentityRepository;
  private final UserMapper userMapper;
  private final UserService userService;
  private final JwtEncoder jwtEncoder;

  public FirebaseToken getUidFromToken(String idToken)
    throws FirebaseException {
    FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(
      idToken
    );

    return firebaseToken;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    String scope = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.HOURS))
      .subject(authentication.getName())
      .claim("scope", scope)
      .build();

    return this.jwtEncoder.encode(
      JwtEncoderParameters.from(claims)
    ).getTokenValue();
  }

  public String issueToken(UUID id, String role) {
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

  @Override
  public UserResponseDto syncUser(AuthRequestDto authRequestDto) {
    try {
      FirebaseToken firebaseToken = getUidFromToken(authRequestDto.token());
      String uid = firebaseToken.getUid();

      return userIdentityRepository
        .findByAuthProviderAndProviderId(AuthProvider.MICROSOFT, uid)
        .map(UserIdentity::getUser)
        .map(user -> {
          return new UserResponseDto(
            userMapper.toDto(user),
            this.issueToken(user.getId(), user.getUserRole().name())
          );
        })
        .orElseGet(() -> {
          UserDto newUser = userService.createNewUser(
            authRequestDto,
            firebaseToken
          );

          return new UserResponseDto(
            newUser,
            this.issueToken(newUser.id(), newUser.userRole().name())
          );
        });
    } catch (FirebaseException e) {
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "Invalid token given"
      );
    }
  }
}
