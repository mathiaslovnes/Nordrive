package no.ntnu.dto.carmodels;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.dto.extrafeatures.ExtraFeaturesResponse;
import no.ntnu.entity.CarModels;

/**
 * DTO for returning a {@link no.ntnu.entity.CarModels car model} in API responses.
 */
@Schema(description = "Response body representing a car model")
public record CarModelsResponse(

    @Schema(description = "The ID of the car model", example = "1")
    Long id,

    @Schema(description = "The brand of the car", example = "BMW")
    String brand,

    @Schema(description = "The model name of the car", example = "i4 eDrive40")
    String modelName,

    @Schema(description = "The type of the car", example = "SEDAN")
    CarModels.CarType carType,

    @Schema(description = "The production year of the car", example = "2024")
    int productionYear,

    @Schema(description = "The number of passengers the car can carry", example = "5")
    int passengers,

    @Schema(description = "The transmission type of the car", example = "AUTOMATIC")
    CarModels.Transmission transmission,

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

    @Schema(description = "The extra features of the car model")
    List<ExtraFeaturesResponse> extraFeatures

) {}
