package no.ntnu.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/**
 * Represents a car model entity in the system.
 * This class is part of the entity layer and is used to map to the database.
 * It contains fields for the car model's brand, model name, type, production
 * year,
 * passenger count, transmission type, energy source, and extra features.
 */
@Entity
public class CarModels {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The ID of the car model")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "The brand of the car")
  private String brand;

  @Column(nullable = false)
  @Schema(description = "The model name of the car")
  private String modelName;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "The type of the car")
  private CarType carType;

  @Column(nullable = false)
  @Schema(description = "The production year of the car")
  private int productionYear;

  @Column(nullable = false)
  @Schema(description = "The number of passengers the car can carry")
  private int passengers;

  @Column(nullable = false)
  @Schema(description = "Whether the car has automatic transmission")
  private boolean automatic;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "The energy source of the car")
  private EnergySource energySource;

  @ManyToMany
  @JoinTable(name = "car_model_extra_features", joinColumns = @JoinColumn(name = "car_model_id"), inverseJoinColumns = @JoinColumn(name = "extra_feature_id"))
  @Schema(description = "The extra features of the car model")
  @JsonManagedReference
  private Set<ExtraFeatures> extraFeatures;

  // Enums

  public enum CarType {
    SEDAN, HATCHBACK, SUV, TRUCK, COUPE, CONVERTIBLE, LUXURY, MINIVAN, SPORTS, CROSSOVER, STATION_WAGON
  }

  public enum EnergySource {
    GAS, DIESEL, HYBRID, ELECTRIC
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public CarType getCarType() {
    return carType;
  }

  public void setCarType(CarType carType) {
    this.carType = carType;
  }

  public int getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(int productionYear) {
    this.productionYear = productionYear;
  }

  public int getPassengers() {
    return passengers;
  }

  public void setPassengers(int passengers) {
    this.passengers = passengers;
  }

  public boolean isAutomatic() {
    return automatic;
  }

  public void setAutomatic(boolean automatic) {
    this.automatic = automatic;
  }

  public EnergySource getEnergySource() {
    return energySource;
  }

  public void setEnergySource(EnergySource energySource) {
    this.energySource = energySource;
  }

  public Set<ExtraFeatures> getExtraFeatures() {
    return extraFeatures;
  }

  public void setExtraFeatures(Set<ExtraFeatures> extraFeatures) {
    this.extraFeatures = extraFeatures;
  }
}
