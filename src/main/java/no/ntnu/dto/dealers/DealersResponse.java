package no.ntnu.dto.dealers;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.entity.Accounts;

/**
 * DTO for returning a {@link no.ntnu.entity.Dealers dealer} in API responses.
 */
@Schema(description = "Response body representing a dealer")
public record DealersResponse(

    @Schema(description = "The ID of the dealer", example = "1")
    Long id,

    @Schema(description = "The email of the dealer", example = "dealer@motors.com")
    String email,

    @Schema(description = "The phone number of the dealer", example = "+4798765432")
    String phoneNumber,

    @Schema(description = "The role of the dealer", example = "ROLE_DEALER")
    Accounts.Role role,

    @Schema(description = "The creation time of the account")
    LocalDateTime createdAt,

    @Schema(description = "The company name of the dealer", example = "Nordic Motors AS")
    String companyName

) {}
