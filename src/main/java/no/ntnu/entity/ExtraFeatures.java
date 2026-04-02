package no.ntnu.entity.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Represents an extra feature that can be associated with {@link CarModels car
 * models}, such as Bluetooth, DAB radio, or heated seats.
 */
@Entity
public class ExtraFeatures {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The ID of the extra feature")
  private Long id;

  @Column(nullable = false, length = 32)
  @Schema(description = "The name of the extra feature")
  private String name;

  @Column(length = 255)
  @Schema(description = "The description of the extra feature")
  private String description;

  @ManyToMany(mappedBy = "extraFeatures")
  @Schema(description = "The car models that have this extra feature")
  @JsonBackReference
  private Set<CarModels> carModels;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<CarModels> getCarModels() {
    return carModels;
  }

  public void setCarModels(Set<CarModels> carModels) {
    this.carModels = carModels;
  }
}
