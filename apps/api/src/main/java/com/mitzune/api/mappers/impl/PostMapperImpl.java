package com.mitzune.api.mappers.impl;

import com.mitzune.api.domains.dto.PostDto;
import com.mitzune.api.domains.entities.PostEntity;
import com.mitzune.api.mappers.PostMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapperImpl implements PostMapper {

  @Override
  public PostEntity fromDto(PostDto dto) {
    return new PostEntity(dto.id(), dto.authorName(), dto.message());
  }

  @Override
  public PostDto toDto(PostEntity fromDto) {
    return new PostDto(
      fromDto.getId(),
      fromDto.getAuthorName(),
      fromDto.getMessage()
    );
  }
}
