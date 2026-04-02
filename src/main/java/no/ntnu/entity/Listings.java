package no.ntnu.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

/**
 * Represents a specific car for sale, linking a {@link CarModels car model}
 * to a {@link Dealers dealer} with listing-specific details.
 */
@Entity
public class Listings {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The ID of the listing")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "car_model_id", nullable = false)
  @Schema(description = "The car model of this listing")
  private CarModels carModel;

  @ManyToOne
  @JoinColumn(name = "dealer_id", nullable = false)
  @Schema(description = "The dealer selling this car")
  private Dealers dealer;

  @Column(length = 7)
  @Schema(description = "The plate number of the car")
  private String plateNumber;

  @Column(nullable = false, precision = 10, scale = 2)
  @Schema(description = "The price of the car")
  private BigDecimal price;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "The availability status of the listing")
  private Availability availability = Availability.AVAILABLE;

  @Schema(description = "The date from which the car is available")
  private LocalDateTime availableFrom;

  @Column(nullable = false)
  @Schema(description = "Whether the listing is visible to buyers")
  private boolean visible = true;

  @Column(nullable = false)
  @Schema(description = "The color of the car")
  private String color;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "Whether the car is new or secondhand")
  private Condition condition;

  @Column(nullable = false)
  @Schema(description = "The mileage of the car in kilometers")
  private int mileage;

  // Enums

  public enum Availability {
    AVAILABLE, IN_TRANSIT, SOLD
  }

  public enum Condition {
    NEW, SECONDHAND
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CarModels getCarModel() {
    return carModel;
  }

  public void setCarModel(CarModels carModel) {
    this.carModel = carModel;
  }

  public Dealers getDealer() {
    return dealer;
  }

  public void setDealer(Dealers dealer) {
    this.dealer = dealer;
  }

  public String getPlateNumber() {
    return plateNumber;
  }

  public void setPlateNumber(String plateNumber) {
    this.plateNumber = plateNumber;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Availability getAvailability() {
    return availability;
  }

  public void setAvailability(Availability availability) {
    this.availability = availability;
  }

  public LocalDateTime getAvailableFrom() {
    return availableFrom;
  }

  public void setAvailableFrom(LocalDateTime availableFrom) {
    this.availableFrom = availableFrom;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Condition getCondition() {
    return condition;
  }

  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  public int getMileage() {
    return mileage;
  }

  public void setMileage(int mileage) {
    this.mileage = mileage;
  }
}
