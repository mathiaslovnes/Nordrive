package no.ntnu.entity;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/**
 * Represents a user entity in the system.
 * Extends {@link Accounts} with additional fields for first name and last name.
 */
@Entity
public class Users extends Accounts {

  @Column(nullable = false)
  @Schema(description = "The first name of the user")
  private String firstName;

  @Column(nullable = false)
  @Schema(description = "The last name of the user")
  private String lastName;

  @ManyToMany
  @JoinTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "listing_id"))
  @Schema(description = "The user's favorite listings")
  private Set<Listings> favoriteListings;

  public Users() {
    super(Role.ROLE_USER);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<Listings> getFavoriteListings() {
    return favoriteListings;
  }

  public void setFavoriteListings(Set<Listings> favoriteListings) {
    this.favoriteListings = favoriteListings;
  }
}
