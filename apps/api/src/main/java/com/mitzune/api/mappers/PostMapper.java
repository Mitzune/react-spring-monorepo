package com.mitzune.api.mappers;

import com.mitzune.api.domains.dto.PostDto;
import com.mitzune.api.domains.entities.PostEntity;

public interface PostMapper {
  PostEntity fromDto(PostDto dto);

  PostDto toDto(PostEntity post);
}
