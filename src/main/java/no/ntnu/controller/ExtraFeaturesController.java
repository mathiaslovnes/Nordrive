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
import no.ntnu.dto.extrafeatures.ExtraFeaturesRequest;
import no.ntnu.dto.extrafeatures.ExtraFeaturesResponse;
import no.ntnu.service.ExtraFeaturesService;

/**
 * REST controller for managing extra features.
 */
@RestController
@Validated
@Tag(name = "Extra Features", description = "Endpoints for managing extra features")
@RequestMapping("/api/extra-features")
public class ExtraFeaturesController {

  private final ExtraFeaturesService extraFeaturesService;

  public ExtraFeaturesController(ExtraFeaturesService extraFeaturesService) {
    this.extraFeaturesService = extraFeaturesService;
  }

  /**
   * Retrieves all extra features.
   *
   * @return a list of all extra features
   */
  @GetMapping
  @Operation(summary = "Get all extra features")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Extra features retrieved")
  })
  public ResponseEntity<List<ExtraFeaturesResponse>> getAll() {
    return ResponseEntity.ok(extraFeaturesService.getAll());
  }

  /**
   * Retrieves an extra feature by its ID.
   *
   * @param id the ID of the extra feature
   * @return the extra feature
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get an extra feature by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Extra feature found"),
      @ApiResponse(responseCode = "404", description = "Extra feature not found")
  })
  public ResponseEntity<ExtraFeaturesResponse> getById(
      @Parameter(description = "ID of the extra feature", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(extraFeaturesService.getById(id));
  }

  /**
   * Creates a new extra feature.
   *
   * @param request the extra feature data
   * @return the created extra feature
   */
  @PostMapping
  @Operation(summary = "Create a new extra feature")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Extra feature created"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  public ResponseEntity<ExtraFeaturesResponse> create(
      @Valid @RequestBody ExtraFeaturesRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(extraFeaturesService.create(request));
  }

  /**
   * Updates an existing extra feature.
   *
   * @param id      the ID of the extra feature to update
   * @param request the updated extra feature data
   * @return the updated extra feature
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update an extra feature by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Extra feature updated"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Extra feature not found")
  })
  public ResponseEntity<ExtraFeaturesResponse> update(
      @Parameter(description = "ID of the extra feature", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Valid @RequestBody ExtraFeaturesRequest request) {
    return ResponseEntity.ok(extraFeaturesService.update(id, request));
  }

  /**
   * Deletes an extra feature by its ID.
   *
   * @param id the ID of the extra feature to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an extra feature by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Extra feature deleted"),
      @ApiResponse(responseCode = "404", description = "Extra feature not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the extra feature", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    extraFeaturesService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
