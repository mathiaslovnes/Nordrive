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
import no.ntnu.dto.dealers.DealersCreateRequest;
import no.ntnu.dto.dealers.DealersResponse;
import no.ntnu.dto.dealers.DealersUpdateRequest;
import no.ntnu.service.DealersService;

/**
 * REST controller for managing dealers.
 */
@RestController
@Validated
@Tag(name = "Dealers", description = "Endpoints for managing dealers")
@RequestMapping("/api/dealers")
public class DealersController {

  private final DealersService dealersService;

  public DealersController(DealersService dealersService) {
    this.dealersService = dealersService;
  }

  /**
   * Retrieves all dealers.
   *
   * @return a list of all dealers
   */
  @GetMapping
  @Operation(summary = "Get all dealers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Dealers retrieved")
  })
  public ResponseEntity<List<DealersResponse>> getAll() {
    return ResponseEntity.ok(dealersService.getAll());
  }

  /**
   * Retrieves a dealer by its ID.
   *
   * @param id the ID of the dealer
   * @return the dealer
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a dealer by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Dealer found"),
      @ApiResponse(responseCode = "404", description = "Dealer not found")
  })
  public ResponseEntity<DealersResponse> getById(
      @Parameter(description = "ID of the dealer", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(dealersService.getById(id));
  }

  /**
   * Creates a new dealer.
   *
   * @param request the dealer data
   * @return the created dealer
   */
  @PostMapping
  @Operation(summary = "Create a new dealer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Dealer created"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  public ResponseEntity<DealersResponse> create(
      @Valid @RequestBody DealersCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(dealersService.create(request));
  }

  /**
   * Updates an existing dealer.
   *
   * @param id      the ID of the dealer to update
   * @param request the updated dealer data
   * @return the updated dealer
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a dealer by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Dealer updated"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Dealer not found")
  })
  public ResponseEntity<DealersResponse> update(
      @Parameter(description = "ID of the dealer", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Valid @RequestBody DealersUpdateRequest request) {
    return ResponseEntity.ok(dealersService.update(id, request));
  }

  /**
   * Soft-deletes a dealer by its ID.
   *
   * @param id the ID of the dealer to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a dealer by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Dealer deleted"),
      @ApiResponse(responseCode = "404", description = "Dealer not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the dealer", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    dealersService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
