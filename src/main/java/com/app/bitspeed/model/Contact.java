package com.app.bitspeed.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "contact")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "linked_id")
  private int linkedId;

  @Enumerated(EnumType.STRING)
  private LinkPrecedence linkPrecedence;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  public enum LinkPrecedence {
    primary,
    secondary
  }

  @PrePersist
  protected void onCreate() {
    createdAt = updatedAt = Timestamp.valueOf(LocalDateTime.now());
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Timestamp.valueOf(LocalDateTime.now());
  }

}
