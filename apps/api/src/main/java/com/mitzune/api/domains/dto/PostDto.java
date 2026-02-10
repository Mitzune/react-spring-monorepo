package com.mitzune.api.domains.dto;

import java.util.UUID;

public record PostDto(UUID id, String authorName, String message) {}
