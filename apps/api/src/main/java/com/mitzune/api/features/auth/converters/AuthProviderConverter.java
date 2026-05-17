package com.mitzune.api.features.auth.converters;

import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderConverter implements Converter<String, AuthProvider> {

  @Override
  public AuthProvider convert(String source) {
    return AuthProvider.fromValue(source);
  }
}
