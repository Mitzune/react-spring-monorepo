package com.mitzune.api.features.auth.repository;

import com.mitzune.api.features.auth.entity.RefreshTokenSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenSessionRepository
  extends JpaRepository<RefreshTokenSession, Long>
{
  Optional<RefreshTokenSession> findByTokenHash(String hash);
}
