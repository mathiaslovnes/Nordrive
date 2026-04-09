package no.ntnu.dto.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.dto.listings.ListingsResponse;
import no.ntnu.dto.users.UsersResponse;
import no.ntnu.entity.Orders;

/**
 * DTO for returning an {@link no.ntnu.entity.Orders order} in API responses.
 */
@Schema(description = "Response body representing an order")
public record OrdersResponse(

    @Schema(description = "The ID of the order", example = "1")
    Long id,

    @Schema(description = "The buyer who placed the order")
    UsersResponse buyer,

    @Schema(description = "The listing being purchased")
    ListingsResponse listing,

    @Schema(description = "The purchase price at the time of order", example = "499000.00")
    BigDecimal purchasePrice,

    @Schema(description = "The date the order was placed")
    LocalDateTime orderedAt,

    @Schema(description = "The status of the order", example = "PENDING")
    Orders.Status status

) {}
