package com.mitzune.api.features.auth.v1.dto;

public record RefreshTokenPair(String rawToken, String tokenHash) {}
