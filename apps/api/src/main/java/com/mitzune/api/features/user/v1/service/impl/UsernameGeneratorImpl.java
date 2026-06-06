package com.mitzune.api.features.user.v1.service.impl;

import com.mitzune.api.features.user.v1.service.UsernameGenerator;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class UsernameGeneratorImpl implements UsernameGenerator {

  private static final Random RANDOM = new SecureRandom();

  private static final String[] ADJECTIVES = {
    "Brave",
    "Swift",
    "Happy",
    "Quiet",
    "Clever",
    "Bright",
  };

  private static final String[] NOUNS = {
    "Panda",
    "Tiger",
    "Hawk",
    "River",
    "Ocean",
    "Fox",
  };

  @Override
  public String generate() {
    return (
      ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)] +
      NOUNS[RANDOM.nextInt(NOUNS.length)] +
      RANDOM.nextInt(10000)
    );
  }
}
