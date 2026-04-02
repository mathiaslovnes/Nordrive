package no.ntnu.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Represents a dealer entity in the system.
 * This class is part of the entity layer and is used to map to the database.
 * It contains fields for the dealer's company name.
 */
@Entity
public class Dealers extends Accounts {
  @Column(nullable = false)
  @Schema(description = "The name of the company of the dealer")
  private String companyName;

  public Dealers() {
    super(Role.ROLE_DEALER);
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
