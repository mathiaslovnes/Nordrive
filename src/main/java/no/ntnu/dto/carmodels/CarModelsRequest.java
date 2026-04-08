package no.ntnu.dto.carmodels;

import java.math.BigDecimal;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.ntnu.entity.CarModels;

/**
 * DTO for creating or updating a {@link no.ntnu.entity.CarModels car model}.
 */
@Schema(description = "Request body for creating or updating a car model")
public record CarModelsRequest(

    @NotBlank(message = "Brand is required")
    @Size(max = 64, message = "Brand must be at most 64 characters")
    @Schema(description = "The brand of the car", example = "BMW")
    String brand,

    @NotBlank(message = "Model name is required")
    @Size(max = 64, message = "Model name must be at most 64 characters")
    @Schema(description = "The model name of the car", example = "i4 eDrive40")
    String modelName,

    @NotNull(message = "Car type is required")
    @Schema(description = "The type of the car", example = "SEDAN")
    CarModels.CarType carType,

    @Min(value = 1886, message = "Production year must be 1886 or later")
    @Schema(description = "The production year of the car", example = "2024")
    int productionYear,

    @Min(value = 1, message = "Passengers must be at least 1")
    @Schema(description = "The number of passengers the car can carry", example = "5")
    int passengers,

    @NotNull(message = "Transmission is required")
    @Schema(description = "The transmission type of the car", example = "AUTOMATIC")
    CarModels.Transmission transmission,

    @NotNull(message = "Energy source is required")
    @Schema(description = "The energy source of the car", example = "ELECTRIC")
    CarModels.EnergySource energySource,

    @Schema(description = "The engine specification of the car", example = "2.0L Turbo")
    String engine,

    @Schema(description = "Battery capacity in kWh", example = "83.9")
    BigDecimal batteryCapacityKwh,

    @Schema(description = "Range in kilometers", example = "590")
    Integer rangeKm,

    @Schema(description = "The range measurement standard", example = "WLTP")
    CarModels.RangeStandard rangeStandard,

    @Schema(description = "Top speed in km/h", example = "190")
    Integer topSpeedKmh,

    @Schema(description = "Acceleration from 0-100 km/h in seconds", example = "5.6")
    BigDecimal acceleration,

    @Schema(description = "IDs of extra features to associate with this car model", example = "[1, 2, 3]")
    Set<Long> extraFeatureIds

) {}
