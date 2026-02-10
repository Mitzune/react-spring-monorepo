package com.mitzune.api.controllers;

import com.mitzune.api.domains.dto.PostDto;
import com.mitzune.api.domains.entities.PostEntity;
import com.mitzune.api.mappers.PostMapper;
import com.mitzune.api.services.PostService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/posts")
public class PostController {

  public final PostService postService;
  public final PostMapper postMapper;

  public PostController(PostService postService, PostMapper postMapper) {
    this.postService = postService;
    this.postMapper = postMapper;
  }

  @GetMapping
  public List<PostDto> getPosts() {
    return postService.listPosts().stream().map(postMapper::toDto).toList();
  }

  @PostMapping
  public PostDto createPost(@RequestBody PostDto postDto) {
    PostEntity createdPost = postService.createPost(
      postMapper.fromDto(postDto)
    );

    return postMapper.toDto(createdPost);
  }
}
