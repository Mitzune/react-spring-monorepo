package com.mitzune.api.features.auth.repository;

import com.mitzune.api.features.auth.entity.UserIdentity;
import com.mitzune.api.features.auth.v1.enums.AuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIdentityRepository
  extends JpaRepository<UserIdentity, Long>
{
  Optional<UserIdentity> findByAuthProviderAndProviderId(
    AuthProvider provider,
    String providerId
  );
}
