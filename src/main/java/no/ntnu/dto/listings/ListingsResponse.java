package no.ntnu.dto.listings;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.dto.carmodels.CarModelsResponse;
import no.ntnu.dto.dealers.DealersResponse;
import no.ntnu.entity.Listings;

/**
 * DTO for returning a {@link no.ntnu.entity.Listings listing} in API responses.
 */
@Schema(description = "Response body representing a listing")
public record ListingsResponse(

    @Schema(description = "The ID of the listing", example = "1")
    Long id,

    @Schema(description = "The car model of this listing")
    CarModelsResponse carModel,

    @Schema(description = "The dealer selling this car")
    DealersResponse dealer,

    @Schema(description = "The plate number of the car", example = "AB12345")
    String plateNumber,

    @Schema(description = "The price of the car", example = "499000.00")
    BigDecimal price,

    @Schema(description = "The availability status of the listing", example = "AVAILABLE")
    Listings.Availability availability,

    @Schema(description = "The date from which the car is available")
    LocalDateTime availableFrom,

    @Schema(description = "Whether the listing is visible to buyers", example = "true")
    boolean visible,

    @Schema(description = "The color of the car", example = "Black")
    String color,

    @Schema(description = "Whether the car is new or secondhand", example = "NEW")
    Listings.Condition condition,

    @Schema(description = "The mileage of the car in kilometers", example = "0")
    int mileage,

    @Schema(description = "URL or path to the car image")
    String imageUrl,

    @Schema(description = "URL to the original advertisement the image was sourced from")
    String sourceUrl

) {}
