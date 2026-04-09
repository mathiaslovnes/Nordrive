package no.ntnu.dto.orders;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for creating an {@link no.ntnu.entity.Orders order}.
 */
@Schema(description = "Request body for creating an order")
public record OrdersRequest(

    @NotNull(message = "Buyer ID is required")
    @Positive(message = "Buyer ID must be positive")
    @Schema(description = "The ID of the buyer", example = "1")
    Long buyerId,

    @NotNull(message = "Listing ID is required")
    @Positive(message = "Listing ID must be positive")
    @Schema(description = "The ID of the listing", example = "1")
    Long listingId

) {}
