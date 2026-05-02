package com.mitzune.api.features.user.v1.controller;

import com.mitzune.api.features.user.v1.dto.UserDto;
import com.mitzune.api.features.user.v1.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public UserDto getCurrentUser(Principal principal) {
    return userService.getCurrentUser();
  }
}
