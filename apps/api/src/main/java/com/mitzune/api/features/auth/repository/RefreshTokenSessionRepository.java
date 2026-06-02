package com.mitzune.api.features.auth.repository;

import com.mitzune.api.features.auth.entity.RefreshTokenSession;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenSessionRepository
  extends JpaRepository<RefreshTokenSession, Long>
{
  Optional<RefreshTokenSession> findByTokenHash(String hash);
  List<RefreshTokenSession> findAllByFamilyId(String familyId);

  @Modifying
  @Query(
    "UPDATE RefreshTokenSession s SET s.revokedAt = :now WHERE s.familyId = :familyId AND s.revokedAt IS NULL"
  )
  void revokeFamily(
    @Param("familyId") String familyId,
    @Param("now") Instant now
  );
}
