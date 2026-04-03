package no.ntnu.entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;

import org.hibernate.annotations.SQLRestriction;

/**
 * Represents an account entity in the system.
 * This class is part of the entity layer and is used to map to the database.
 * It contains fields for the account's ID, email, phone number, role, password,
 * creation time, and deletion status.
 *
 * <p>
 * This entity uses soft deletion via the {@code isDeleted} field.
 * The {@code @SQLRestriction} annotation automatically filters out deleted
 * accounts from all standard JPA queries. To retrieve deleted accounts (e.g.
 * for admin
 * audit), use a native query to bypass this filter.
 * </p>
 */
@Entity
@SQLRestriction("is_deleted = false")
@Inheritance(strategy = InheritanceType.JOINED)
public class Accounts {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The ID of the account")
  private Long id;

  @Column(nullable = false, unique = true)
  @Schema(description = "The email of the account")
  private String email;

  @Column(unique = true)
  @Schema(description = "The phone number of the account")
  private String phoneNumber;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "The role of the account")
  private Role role;

  @Column(nullable = false)
  @Schema(description = "The password of the account")
  private String password;

  @Column(nullable = false)
  @Schema(description = "The creation time of the account")
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @Schema(description = "Boolean indicating if the account is deleted")
  private boolean isDeleted = false;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  /**
   * Constructor for the Accounts class.
   * Initializes the role to the specified role.
   */
  public Accounts(Role role) {
    this.role = role;
  }

  /**
   * Default constructor for the Accounts class.
   * Initializes the role to ROLE_USER.
   */
  public Accounts() {
    this.role = Role.ROLE_USER;
  }

  /**
   * Enum representing the different roles an account can have.
   * ROLE_ADMIN: Represents an administrator account.
   * ROLE_DEALER: Represents a provider account.
   * ROLE_USER: Represents a regular user account.
   */
  public enum Role {
    ROLE_ADMIN, ROLE_DEALER, ROLE_USER
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Role getRole() {
    return this.role;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public boolean isDeleted() {
    return this.isDeleted;
  }

  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}
