package no.ntnu.controller;

import java.util.List;
import java.util.Set;

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
import no.ntnu.dto.users.UsersRequest;
import no.ntnu.dto.users.UsersResponse;
import no.ntnu.service.UsersService;

/**
 * REST controller for managing users.
 */
@RestController
@Validated
@Tag(name = "Users", description = "Endpoints for managing users")
@RequestMapping("/api/users")
public class UsersController {

  private final UsersService usersService;

  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  /**
   * Retrieves all users.
   *
   * @return a list of all users
   */
  @GetMapping
  @Operation(summary = "Get all users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users retrieved")
  })
  public ResponseEntity<List<UsersResponse>> getAll() {
    return ResponseEntity.ok(usersService.getAll());
  }

  /**
   * Retrieves a user by its ID.
   *
   * @param id the ID of the user
   * @return the user
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a user by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<UsersResponse> getById(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(usersService.getById(id));
  }

  /**
   * Creates a new user.
   *
   * @param request the user data
   * @return the created user
   */
  @PostMapping
  @Operation(summary = "Create a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  public ResponseEntity<UsersResponse> create(
      @Valid @RequestBody UsersRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(usersService.create(request));
  }

  /**
   * Updates an existing user.
   *
   * @param id      the ID of the user to update
   * @param request the updated user data
   * @return the updated user
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a user by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<UsersResponse> update(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Valid @RequestBody UsersRequest request) {
    return ResponseEntity.ok(usersService.update(id, request));
  }

  /**
   * Soft-deletes a user by its ID.
   *
   * @param id the ID of the user to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a user by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "User deleted"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    usersService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Retrieves a user's favorite listing IDs.
   *
   * @param id the ID of the user
   * @return a set of favorite listing IDs
   */
  @GetMapping("/{id}/favorites")
  @Operation(summary = "Get a user's favorite listings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Favorites retrieved"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<Set<Long>> getFavorites(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(usersService.getFavorites(id));
  }

  /**
   * Adds a listing to a user's favorites.
   *
   * @param id        the ID of the user
   * @param listingId the ID of the listing to add
   * @return 204 No Content on success
   */
  @PostMapping("/{id}/favorites/{listingId}")
  @Operation(summary = "Add a listing to a user's favorites")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Favorite added"),
      @ApiResponse(responseCode = "404", description = "User or listing not found")
  })
  public ResponseEntity<Void> addFavorite(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Parameter(description = "ID of the listing", required = true)
      @Positive(message = "Listing ID must be positive")
      @PathVariable Long listingId) {
    usersService.addFavorite(id, listingId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Removes a listing from a user's favorites.
   *
   * @param id        the ID of the user
   * @param listingId the ID of the listing to remove
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}/favorites/{listingId}")
  @Operation(summary = "Remove a listing from a user's favorites")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Favorite removed"),
      @ApiResponse(responseCode = "404", description = "User or listing not found")
  })
  public ResponseEntity<Void> removeFavorite(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Parameter(description = "ID of the listing", required = true)
      @Positive(message = "Listing ID must be positive")
      @PathVariable Long listingId) {
    usersService.removeFavorite(id, listingId);
    return ResponseEntity.noContent().build();
  }
}
