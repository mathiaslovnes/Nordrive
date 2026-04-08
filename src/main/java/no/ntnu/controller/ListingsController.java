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
import no.ntnu.dto.listings.ListingsRequest;
import no.ntnu.dto.listings.ListingsResponse;
import no.ntnu.service.ListingsService;

/**
 * REST controller for managing listings.
 */
@RestController
@Validated
@Tag(name = "Listings", description = "Endpoints for managing listings")
@RequestMapping("/api/listings")
public class ListingsController {

  private final ListingsService listingsService;

  public ListingsController(ListingsService listingsService) {
    this.listingsService = listingsService;
  }

  /**
   * Retrieves all listings.
   *
   * @return a list of all listings
   */
  @GetMapping
  @Operation(summary = "Get all listings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listings retrieved")
  })
  public ResponseEntity<List<ListingsResponse>> getAll() {
    return ResponseEntity.ok(listingsService.getAll());
  }

  /**
   * Retrieves a listing by its ID.
   *
   * @param id the ID of the listing
   * @return the listing
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a listing by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listing found"),
      @ApiResponse(responseCode = "404", description = "Listing not found")
  })
  public ResponseEntity<ListingsResponse> getById(
      @Parameter(description = "ID of the listing", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(listingsService.getById(id));
  }

  /**
   * Retrieves all listings for a given dealer.
   *
   * @param dealerId the ID of the dealer
   * @return a list of listings for the dealer
   */
  @GetMapping("/dealer/{dealerId}")
  @Operation(summary = "Get all listings for a dealer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listings retrieved"),
      @ApiResponse(responseCode = "404", description = "Dealer not found")
  })
  public ResponseEntity<List<ListingsResponse>> getByDealerId(
      @Parameter(description = "ID of the dealer", required = true)
      @Positive(message = "Dealer ID must be positive")
      @PathVariable Long dealerId) {
    return ResponseEntity.ok(listingsService.getByDealerId(dealerId));
  }

  /**
   * Creates a new listing.
   *
   * @param request the listing data
   * @return the created listing
   */
  @PostMapping
  @Operation(summary = "Create a new listing")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Listing created"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  public ResponseEntity<ListingsResponse> create(
      @Valid @RequestBody ListingsRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(listingsService.create(request));
  }

  /**
   * Updates an existing listing.
   *
   * @param id      the ID of the listing to update
   * @param request the updated listing data
   * @return the updated listing
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a listing by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listing updated"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Listing not found")
  })
  public ResponseEntity<ListingsResponse> update(
      @Parameter(description = "ID of the listing", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Valid @RequestBody ListingsRequest request) {
    return ResponseEntity.ok(listingsService.update(id, request));
  }

  /**
   * Deletes a listing by its ID.
   *
   * @param id the ID of the listing to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a listing by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Listing deleted"),
      @ApiResponse(responseCode = "404", description = "Listing not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the listing", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    listingsService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
