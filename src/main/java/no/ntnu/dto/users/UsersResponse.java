package no.ntnu.dto.users;

import java.time.LocalDateTime;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.entity.Accounts;

/**
 * DTO for returning a {@link no.ntnu.entity.Users user} in API responses.
 */
@Schema(description = "Response body representing a user")
public record UsersResponse(

    @Schema(description = "The ID of the user", example = "1")
    Long id,

    @Schema(description = "The email of the user", example = "john@example.com")
    String email,

    @Schema(description = "The phone number of the user", example = "+4712345678")
    String phoneNumber,

    @Schema(description = "The role of the user", example = "ROLE_USER")
    Accounts.Role role,

    @Schema(description = "The creation time of the account")
    LocalDateTime createdAt,

    @Schema(description = "The first name of the user", example = "John")
    String firstName,

    @Schema(description = "The last name of the user", example = "Doe")
    String lastName,

    @Schema(description = "IDs of the user's favorite listings")
    Set<Long> favoriteListingIds

) {}
