package com.mitzune.api.services.impl;

import com.mitzune.api.domains.entities.PostEntity;
import com.mitzune.api.repositories.PostRepository;
import com.mitzune.api.services.PostService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public List<PostEntity> listPosts() {
    return postRepository.findAll();
  }

  @Override
  public PostEntity createPost(PostEntity postEntity) {
    if (null == postEntity.getAuthorName() || null == postEntity.getMessage()) {
      throw new IllegalArgumentException("Task list title must be present!");
    }

    return postRepository.save(
      new PostEntity(null, postEntity.getAuthorName(), postEntity.getMessage())
    );
  }
}
