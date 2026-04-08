package no.ntnu.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

/**
 * Represents a car purchase order, linking a {@link Users buyer}
 * to a {@link Listings listing}.
 *
 * <p>
 * This entity uses soft deletion via the {@code isDeleted} field.
 * The {@code @SQLRestriction} annotation automatically filters out deleted
 * orders from all standard JPA queries.
 * </p>
 */
@Entity
@SQLRestriction("is_deleted = false")
public class Orders {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The ID of the order")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "buyer_id", nullable = false)
  @Schema(description = "The buyer who placed the order")
  private Users buyer;

  @ManyToOne
  @JoinColumn(name = "listing_id", nullable = false)
  @Schema(description = "The listing being purchased")
  private Listings listing;

  @Column(nullable = false, precision = 10, scale = 2)
  @Schema(description = "The purchase price at the time of order")
  private BigDecimal purchasePrice;

  @Column(nullable = false)
  @Schema(description = "The date the order was placed")
  private LocalDateTime orderedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "The status of the order")
  private Status status = Status.PENDING;

  @Column(nullable = false)
  @Schema(description = "Boolean indicating if the order is deleted")
  private boolean isDeleted = false;

  @PrePersist
  protected void onCreate() {
    this.orderedAt = LocalDateTime.now();
  }

  // Enums

  public enum Status {
    PENDING, COMPLETED, CANCELED
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Users getBuyer() {
    return buyer;
  }

  public void setBuyer(Users buyer) {
    this.buyer = buyer;
  }

  public Listings getListing() {
    return listing;
  }

  public void setListing(Listings listing) {
    this.listing = listing;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public LocalDateTime getOrderedAt() {
    return orderedAt;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}
