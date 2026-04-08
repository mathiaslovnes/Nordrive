package no.ntnu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import no.ntnu.dto.carmodels.CarModelsRequest;
import no.ntnu.dto.carmodels.CarModelsResponse;
import no.ntnu.service.CarModelsService;

/**
 * REST controller for managing car models.
 */
@RestController
@Validated
@Tag(name = "Car Models", description = "Endpoints for managing car models")
@RequestMapping("/api/car-models")
public class CarModelsController {

  private final CarModelsService carModelsService;

  public CarModelsController(CarModelsService carModelsService) {
    this.carModelsService = carModelsService;
  }

  /**
   * Retrieves all car models.
   *
   * @return a list of all car models
   */
  @GetMapping
  @Operation(summary = "Get all car models")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car models retrieved")
  })
  public ResponseEntity<List<CarModelsResponse>> getAll() {
    return ResponseEntity.ok(carModelsService.getAll());
  }

  /**
   * Retrieves a car model by its ID.
   *
   * @param id the ID of the car model
   * @return the car model
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a car model by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car model found"),
      @ApiResponse(responseCode = "404", description = "Car model not found")
  })
  public ResponseEntity<CarModelsResponse> getById(
      @Parameter(description = "ID of the car model", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(carModelsService.getById(id));
  }

  /**
   * Creates a new car model.
   *
   * @param request the car model data
   * @return the created car model
   */
  @PostMapping
  @Operation(summary = "Create a new car model")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Car model created"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  public ResponseEntity<CarModelsResponse> create(
      @Valid @RequestBody CarModelsRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(carModelsService.create(request));
  }

  /**
   * Updates an existing car model.
   *
   * @param id      the ID of the car model to update
   * @param request the updated car model data
   * @return the updated car model
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a car model by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car model updated"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Car model not found")
  })
  public ResponseEntity<CarModelsResponse> update(
      @Parameter(description = "ID of the car model", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Valid @RequestBody CarModelsRequest request) {
    return ResponseEntity.ok(carModelsService.update(id, request));
  }

  /**
   * Deletes a car model by its ID.
   *
   * @param id the ID of the car model to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a car model by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Car model deleted"),
      @ApiResponse(responseCode = "404", description = "Car model not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the car model", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    carModelsService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
