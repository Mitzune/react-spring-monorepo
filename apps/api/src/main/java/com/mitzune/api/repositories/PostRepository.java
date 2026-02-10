package com.mitzune.api.repositories;

import com.mitzune.api.domains.entities.PostEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {}
