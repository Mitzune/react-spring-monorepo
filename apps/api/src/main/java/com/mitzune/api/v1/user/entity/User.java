package com.mitzune.api.v1.user.entity;

import com.mitzune.api.core.entity.BaseEntity;
import com.mitzune.api.v1.user.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false)
  private UUID id;

  @Column(
    name = "display_name",
    updatable = false,
    length = 50,
    nullable = false
  )
  private String displayName;

  @Column(name = "email", updatable = false, nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", updatable = true, nullable = false)
  private UserRole userRole;
}
