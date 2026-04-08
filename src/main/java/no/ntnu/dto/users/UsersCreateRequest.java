package no.ntnu.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a {@link no.ntnu.entity.Users user}.
 */
@Schema(description = "Request body for creating a user")
public record UsersCreateRequest(

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must be at most 255 characters")
    @Schema(description = "The email of the user", example = "john@example.com")
    String email,

    @Size(max = 32, message = "Phone number must be at most 32 characters")
    @Schema(description = "The phone number of the user", example = "+4712345678")
    String phoneNumber,

    @NotBlank(message = "Password is required")
    @Size(min = 1, max = 255, message = "Password must be between 1 and 255 characters")
    @Schema(description = "The password of the user")
    String password,

    @NotBlank(message = "First name is required")
    @Size(max = 64, message = "First name must be at most 64 characters")
    @Schema(description = "The first name of the user", example = "John")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 64, message = "Last name must be at most 64 characters")
    @Schema(description = "The last name of the user", example = "Doe")
    String lastName

) {}
