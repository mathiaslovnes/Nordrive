package no.ntnu.dto.accounts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating an account's password.
 */
@Schema(description = "Request body for updating an account's password")
public record UpdatePasswordRequest(

    @NotBlank(message = "Old password is required")
    @Schema(description = "The current password of the account")
    String oldPassword,

    @NotBlank(message = "New password is required")
    @Size(min = 1, max = 255, message = "New password must be between 1 and 255 characters")
    @Schema(description = "The new password for the account")
    String newPassword

) {}
