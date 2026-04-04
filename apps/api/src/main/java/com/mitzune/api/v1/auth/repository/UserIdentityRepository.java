package com.mitzune.api.v1.auth.repository;

import com.mitzune.api.v1.auth.entity.UserIdentity;
import com.mitzune.api.v1.auth.enums.AuthProvider;
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
