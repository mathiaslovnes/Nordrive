package no.ntnu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import no.ntnu.dto.accounts.AccountsResponse;
import no.ntnu.dto.accounts.UpdatePasswordRequest;
import no.ntnu.service.AccountsService;

/**
 * REST controller for managing accounts.
 */
@RestController
@Validated
@Tag(name = "Accounts", description = "Endpoints for managing accounts")
@RequestMapping("/api/accounts")
public class AccountsController {

  private final AccountsService accountsService;

  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  /**
   * Retrieves all accounts.
   *
   * @return a list of all accounts
   */
  @GetMapping
  @Operation(summary = "Get all accounts")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Accounts retrieved")
  })
  public ResponseEntity<List<AccountsResponse>> getAll() {
    return ResponseEntity.ok(accountsService.getAll());
  }

  /**
   * Retrieves an account by its ID.
   *
   * @param id the ID of the account
   * @return the account
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get an account by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Account found"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public ResponseEntity<AccountsResponse> getById(
      @Parameter(description = "ID of the account", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(accountsService.getById(id));
  }

  /**
   * Updates the password for an account.
   *
   * @param id      the ID of the account
   * @param request the password update request
   * @return 204 No Content on success
   */
  @PutMapping("/{id}/password")
  @Operation(summary = "Update an account's password")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Password updated"),
      @ApiResponse(responseCode = "400", description = "Old password is incorrect"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public ResponseEntity<Void> updatePassword(
      @Parameter(description = "ID of the account", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Valid @RequestBody UpdatePasswordRequest request) {
    accountsService.updatePassword(id, request);
    return ResponseEntity.noContent().build();
  }

  /**
   * Soft-deletes an account by its ID.
   *
   * @param id the ID of the account to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an account by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Account deleted"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the account", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    accountsService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
