package no.ntnu.dto.listings;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import no.ntnu.entity.Listings;

/**
 * DTO for creating or updating a {@link no.ntnu.entity.Listings listing}.
 */
@Schema(description = "Request body for creating or updating a listing")
public record ListingsRequest(

    @NotNull(message = "Car model ID is required")
    @Positive(message = "Car model ID must be positive")
    @Schema(description = "The ID of the car model", example = "1")
    Long carModelId,

    @NotNull(message = "Dealer ID is required")
    @Positive(message = "Dealer ID must be positive")
    @Schema(description = "The ID of the dealer", example = "1")
    Long dealerId,

    @Size(max = 7, message = "Plate number must be at most 7 characters")
    @Schema(description = "The plate number of the car", example = "AB12345")
    String plateNumber,

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    @Schema(description = "The price of the car", example = "499000.00")
    BigDecimal price,

    @Schema(description = "The availability status of the listing", example = "AVAILABLE")
    Listings.Availability availability,

    @Schema(description = "The date from which the car is available")
    LocalDateTime availableFrom,

    @Schema(description = "Whether the listing is visible to buyers", example = "true")
    Boolean visible,

    @NotBlank(message = "Color is required")
    @Schema(description = "The color of the car", example = "Black")
    String color,

    @NotNull(message = "Condition is required")
    @Schema(description = "Whether the car is new or secondhand", example = "NEW")
    Listings.Condition condition,

    @Min(value = 0, message = "Mileage must be non-negative")
    @Schema(description = "The mileage of the car in kilometers", example = "0")
    int mileage,

    @Schema(description = "URL or path to the car image")
    String imageUrl,

    @Schema(description = "URL to the original advertisement the image was sourced from")
    String sourceUrl

) {}
