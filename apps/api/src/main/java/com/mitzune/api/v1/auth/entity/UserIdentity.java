package com.mitzune.api.v1.auth.entity;

import com.mitzune.api.core.entity.BaseEntity;
import com.mitzune.api.v1.auth.enums.AuthProvider;
import com.mitzune.api.v1.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_identities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserIdentity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false)
  private AuthProvider authProvider;

  @Column(name = "provider_id", nullable = false)
  private String providerId;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;
}
