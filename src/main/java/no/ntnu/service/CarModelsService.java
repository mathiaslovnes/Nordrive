package no.ntnu.service;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import no.ntnu.dto.carmodels.CarModelsRequest;
import no.ntnu.dto.carmodels.CarModelsResponse;
import no.ntnu.dto.extrafeatures.ExtraFeaturesResponse;
import no.ntnu.entity.CarModels;
import no.ntnu.entity.ExtraFeatures;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.CarModelsRepository;
import no.ntnu.repository.ExtraFeaturesRepository;

/**
 * Service for managing {@link CarModels} entities.
 */
@Service
public class CarModelsService {
  private static final Logger logger = LoggerFactory.getLogger(CarModelsService.class);

  private final CarModelsRepository carModelsRepository;
  private final ExtraFeaturesRepository extraFeaturesRepository;

  public CarModelsService(CarModelsRepository carModelsRepository,
      ExtraFeaturesRepository extraFeaturesRepository) {
    this.carModelsRepository = carModelsRepository;
    this.extraFeaturesRepository = extraFeaturesRepository;
  }

  /**
   * Retrieves all car models.
   *
   * @return a list of all car models
   */
  public List<CarModelsResponse> getAll() {
    logger.debug("Retrieving all car models");
    return carModelsRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves a car model by its ID.
   *
   * @param id the ID of the car model
   * @return the car model
   * @throws NotFoundException if no car model with the given ID exists
   */
  public CarModelsResponse getById(Long id) {
    logger.debug("Retrieving car model with ID: {}", id);
    return carModelsRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "Car model with ID " + id + " not found"));
  }

  /**
   * Creates a new car model.
   *
   * @param request the car model data
   * @return the created car model
   */
  public CarModelsResponse create(CarModelsRequest request) {
    logger.info("Creating car model: {} {}", request.brand(), request.modelName());
    CarModels entity = new CarModels();
    applyRequest(entity, request);
    CarModels saved = carModelsRepository.save(entity);
    logger.info("Created car model with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Updates an existing car model.
   *
   * @param id      the ID of the car model to update
   * @param request the updated car model data
   * @return the updated car model
   * @throws NotFoundException if no car model with the given ID exists
   */
  public CarModelsResponse update(Long id, CarModelsRequest request) {
    logger.info("Updating car model with ID: {}", id);
    CarModels existing = carModelsRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Car model with ID " + id + " not found"));
    applyRequest(existing, request);
    CarModels saved = carModelsRepository.save(existing);
    logger.info("Updated car model with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Deletes a car model by its ID.
   *
   * @param id the ID of the car model to delete
   * @throws NotFoundException if no car model with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting car model with ID: {}", id);
    if (!carModelsRepository.existsById(id)) {
      throw new NotFoundException("Car model with ID " + id + " not found");
    }
    carModelsRepository.deleteById(id);
    logger.info("Deleted car model with ID: {}", id);
  }

  /**
   * Applies the fields from a request DTO to a car model entity.
   *
   * @param entity  the entity to update
   * @param request the request data
   */
  private void applyRequest(CarModels entity, CarModelsRequest request) {
    entity.setBrand(request.brand());
    entity.setModelName(request.modelName());
    entity.setCarType(request.carType());
    entity.setProductionYear(request.productionYear());
    entity.setPassengers(request.passengers());
    entity.setTransmission(request.transmission());
    entity.setEnergySource(request.energySource());
    entity.setEngine(request.engine());
    entity.setBatteryCapacityKwh(request.batteryCapacityKwh());
    entity.setRangeKm(request.rangeKm());
    entity.setRangeStandard(request.rangeStandard());
    entity.setTopSpeedKmh(request.topSpeedKmh());
    entity.setAcceleration(request.acceleration());

    if (request.extraFeatureIds() != null && !request.extraFeatureIds().isEmpty()) {
      entity.setExtraFeatures(
          new HashSet<>(extraFeaturesRepository.findAllById(request.extraFeatureIds())));
    } else {
      entity.setExtraFeatures(null);
    }
  }

  /**
   * Converts a {@link CarModels} entity to a {@link CarModelsResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private CarModelsResponse toResponse(CarModels entity) {
    List<ExtraFeaturesResponse> extraFeatures = entity.getExtraFeatures() == null
        ? List.of()
        : entity.getExtraFeatures().stream()
            .map(ef -> new ExtraFeaturesResponse(ef.getId(), ef.getName(), ef.getDescription()))
            .toList();

    return new CarModelsResponse(
        entity.getId(),
        entity.getBrand(),
        entity.getModelName(),
        entity.getCarType(),
        entity.getProductionYear(),
        entity.getPassengers(),
        entity.getTransmission(),
        entity.getEnergySource(),
        entity.getEngine(),
        entity.getBatteryCapacityKwh(),
        entity.getRangeKm(),
        entity.getRangeStandard(),
        entity.getTopSpeedKmh(),
        entity.getAcceleration(),
        extraFeatures);
  }
}
