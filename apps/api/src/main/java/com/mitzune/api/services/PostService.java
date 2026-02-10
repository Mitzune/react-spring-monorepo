package com.mitzune.api.services;

import com.mitzune.api.domains.entities.PostEntity;
import java.util.List;

public interface PostService {
  public List<PostEntity> listPosts();
  public PostEntity createPost(PostEntity postEntity);
}
