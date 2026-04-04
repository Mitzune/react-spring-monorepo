package com.mitzune.api.v1.auth.controller;

import com.mitzune.api.v1.auth.dto.AuthRequestDto;
import com.mitzune.api.v1.auth.enums.AuthProvider;
import com.mitzune.api.v1.auth.service.AuthService;
import com.mitzune.api.v1.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

  private final AuthService authService;

  @PostMapping("/Microsoft")
  public ResponseEntity<UserDto> createAccountWithMicrosoft(
    @Valid @RequestBody AuthRequestDto authRequestDto
  ) {
    AuthRequestDto userRequest = new AuthRequestDto(
      authRequestDto.token(),
      AuthProvider.MICROSOFT
    );
    return ResponseEntity.ok(authService.syncUser(userRequest));
  }
}
