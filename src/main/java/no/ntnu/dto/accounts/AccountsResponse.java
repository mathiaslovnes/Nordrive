package no.ntnu.dto.accounts;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.entity.Accounts;

/**
 * DTO for returning an {@link no.ntnu.entity.Accounts account} in API responses.
 */
@Schema(description = "Response body representing an account")
public record AccountsResponse(

    @Schema(description = "The ID of the account", example = "1")
    Long id,

    @Schema(description = "The email of the account", example = "user@example.com")
    String email,

    @Schema(description = "The phone number of the account", example = "+4712345678")
    String phoneNumber,

    @Schema(description = "The role of the account", example = "ROLE_USER")
    Accounts.Role role,

    @Schema(description = "The creation time of the account")
    LocalDateTime createdAt

) {}
