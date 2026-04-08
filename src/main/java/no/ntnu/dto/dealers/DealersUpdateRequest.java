package no.ntnu.dto.dealers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating a {@link no.ntnu.entity.Dealers dealer}.
 */
@Schema(description = "Request body for updating a dealer")
public record DealersUpdateRequest(

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must be at most 255 characters")
    @Schema(description = "The email of the dealer", example = "dealer@motors.com")
    String email,

    @Size(max = 32, message = "Phone number must be at most 32 characters")
    @Schema(description = "The phone number of the dealer", example = "+4798765432")
    String phoneNumber,

    @NotBlank(message = "Company name is required")
    @Size(max = 128, message = "Company name must be at most 128 characters")
    @Schema(description = "The company name of the dealer", example = "Nordic Motors AS")
    String companyName

) {}
