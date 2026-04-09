package no.ntnu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import no.ntnu.dto.orders.OrdersRequest;
import no.ntnu.dto.orders.OrdersResponse;
import no.ntnu.entity.Orders;
import no.ntnu.service.OrdersService;

/**
 * REST controller for managing orders.
 */
@RestController
@Validated
@Tag(name = "Orders", description = "Endpoints for managing orders")
@RequestMapping("/api/orders")
public class OrdersController {

  private final OrdersService ordersService;

  public OrdersController(OrdersService ordersService) {
    this.ordersService = ordersService;
  }

  /**
   * Retrieves all orders.
   *
   * @return a list of all orders
   */
  @GetMapping
  @Operation(summary = "Get all orders")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders retrieved")
  })
  public ResponseEntity<List<OrdersResponse>> getAll() {
    return ResponseEntity.ok(ordersService.getAll());
  }

  /**
   * Retrieves an order by its ID.
   *
   * @param id the ID of the order
   * @return the order
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get an order by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order found"),
      @ApiResponse(responseCode = "404", description = "Order not found")
  })
  public ResponseEntity<OrdersResponse> getById(
      @Parameter(description = "ID of the order", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    return ResponseEntity.ok(ordersService.getById(id));
  }

  /**
   * Retrieves all orders for a given buyer.
   *
   * @param userId the ID of the buyer
   * @return a list of orders for the buyer
   */
  @GetMapping("/user/{userId}")
  @Operation(summary = "Get all orders for a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders retrieved"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<List<OrdersResponse>> getByUserId(
      @Parameter(description = "ID of the user", required = true)
      @Positive(message = "User ID must be positive")
      @PathVariable Long userId) {
    return ResponseEntity.ok(ordersService.getByUserId(userId));
  }

  /**
   * Retrieves all orders for a given listing.
   *
   * @param listingId the ID of the listing
   * @return a list of orders for the listing
   */
  @GetMapping("/listing/{listingId}")
  @Operation(summary = "Get all orders for a listing")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders retrieved"),
      @ApiResponse(responseCode = "404", description = "Listing not found")
  })
  public ResponseEntity<List<OrdersResponse>> getByListingId(
      @Parameter(description = "ID of the listing", required = true)
      @Positive(message = "Listing ID must be positive")
      @PathVariable Long listingId) {
    return ResponseEntity.ok(ordersService.getByListingId(listingId));
  }

  /**
   * Retrieves all orders for a given dealer.
   *
   * @param dealerId the ID of the dealer
   * @return a list of orders for the dealer
   */
  @GetMapping("/dealer/{dealerId}")
  @Operation(summary = "Get all orders for a dealer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders retrieved")
  })
  public ResponseEntity<List<OrdersResponse>> getByDealerId(
      @Parameter(description = "ID of the dealer", required = true)
      @Positive(message = "Dealer ID must be positive")
      @PathVariable Long dealerId) {
    return ResponseEntity.ok(ordersService.getByDealerId(dealerId));
  }

  /**
   * Creates a new order.
   *
   * @param request the order data
   * @return the created order
   */
  @PostMapping
  @Operation(summary = "Create a new order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Order created"),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "User or listing not found")
  })
  public ResponseEntity<OrdersResponse> create(
      @Valid @RequestBody OrdersRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ordersService.create(request));
  }

  /**
   * Updates the status of an order.
   *
   * @param id     the ID of the order
   * @param status the new status
   * @return the updated order
   */
  @PatchMapping("/{id}/status")
  @Operation(summary = "Update the status of an order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order status updated"),
      @ApiResponse(responseCode = "404", description = "Order not found")
  })
  public ResponseEntity<OrdersResponse> updateStatus(
      @Parameter(description = "ID of the order", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id,
      @Parameter(description = "The new status", required = true)
      @RequestParam Orders.Status status) {
    return ResponseEntity.ok(ordersService.updateStatus(id, status));
  }

  /**
   * Deletes an order by its ID.
   *
   * @param id the ID of the order to delete
   * @return 204 No Content on success
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an order by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Order deleted"),
      @ApiResponse(responseCode = "404", description = "Order not found")
  })
  public ResponseEntity<Void> deleteById(
      @Parameter(description = "ID of the order", required = true)
      @Positive(message = "ID must be positive")
      @PathVariable Long id) {
    ordersService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
