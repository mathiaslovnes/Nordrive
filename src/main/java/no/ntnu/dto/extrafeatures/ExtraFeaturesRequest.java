package no.ntnu.dto.extrafeatures;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating or updating an {@link no.ntnu.entity.ExtraFeatures extra feature}.
 */
@Schema(description = "Request body for creating or updating an extra feature")
public record ExtraFeaturesRequest(

    @NotBlank(message = "Name is required")
    @Size(max = 32, message = "Name must be at most 32 characters")
    @Schema(description = "The name of the extra feature", example = "Heated Seats")
    String name,

    @Size(max = 255, message = "Description must be at most 255 characters")
    @Schema(description = "A description of the extra feature", example = "Front and rear heated seats with three levels")
    String description

) {}
