package com.mitzune.api.domains.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false)
  public UUID id;

  @Column(name = "author_name", nullable = false)
  public String authorName;

  @Column(name = "message", nullable = false)
  public String message;

  public PostEntity() {}

  public PostEntity(UUID id, String authorName, String message) {
    this.id = id;
    this.authorName = authorName;
    this.message = message;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result =
      prime * result + ((authorName == null) ? 0 : authorName.hashCode());
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PostEntity other = (PostEntity) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (authorName == null) {
      if (other.authorName != null) return false;
    } else if (!authorName.equals(other.authorName)) return false;
    if (message == null) {
      if (other.message != null) return false;
    } else if (!message.equals(other.message)) return false;
    return true;
  }

  @Override
  public String toString() {
    return (
      "PostEntity [id=" +
      id +
      ", authorName=" +
      authorName +
      ", message=" +
      message +
      "]"
    );
  }
}
