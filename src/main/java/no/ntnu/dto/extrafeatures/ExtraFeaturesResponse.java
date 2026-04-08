package no.ntnu.dto.extrafeatures;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for returning an {@link no.ntnu.entity.ExtraFeatures extra feature} in API responses.
 */
@Schema(description = "Response body representing an extra feature")
public record ExtraFeaturesResponse(

    @Schema(description = "The ID of the extra feature", example = "1")
    Long id,

    @Schema(description = "The name of the extra feature", example = "Heated Seats")
    String name,

    @Schema(description = "A description of the extra feature", example = "Front and rear heated seats with three levels")
    String description

) {}
