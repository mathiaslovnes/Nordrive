package no.ntnu.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import no.ntnu.dto.carmodels.CarModelsResponse;
import no.ntnu.dto.dealers.DealersResponse;
import no.ntnu.dto.extrafeatures.ExtraFeaturesResponse;
import no.ntnu.dto.listings.ListingsRequest;
import no.ntnu.dto.listings.ListingsResponse;
import no.ntnu.entity.CarModels;
import no.ntnu.entity.Dealers;
import no.ntnu.entity.Listings;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.CarModelsRepository;
import no.ntnu.repository.DealersRepository;
import no.ntnu.repository.ListingsRepository;

/**
 * Service for managing {@link Listings} entities.
 */
@Service
public class ListingsService {
  private static final Logger logger = LoggerFactory.getLogger(ListingsService.class);

  private final ListingsRepository listingsRepository;
  private final CarModelsRepository carModelsRepository;
  private final DealersRepository dealersRepository;

  public ListingsService(ListingsRepository listingsRepository,
      CarModelsRepository carModelsRepository,
      DealersRepository dealersRepository) {
    this.listingsRepository = listingsRepository;
    this.carModelsRepository = carModelsRepository;
    this.dealersRepository = dealersRepository;
  }

  /**
   * Retrieves all listings.
   *
   * @return a list of all listings
   */
  public List<ListingsResponse> getAll() {
    logger.debug("Retrieving all listings");
    return listingsRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves a listing by its ID.
   *
   * @param id the ID of the listing
   * @return the listing
   * @throws NotFoundException if no listing with the given ID exists
   */
  public ListingsResponse getById(Long id) {
    logger.debug("Retrieving listing with ID: {}", id);
    return listingsRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "Listing with ID " + id + " not found"));
  }

  /**
   * Retrieves all listings for a given dealer.
   *
   * @param dealerId the ID of the dealer
   * @return a list of listings for the dealer
   * @throws NotFoundException if no dealer with the given ID exists
   */
  public List<ListingsResponse> getByDealerId(Long dealerId) {
    logger.debug("Retrieving listings for dealer with ID: {}", dealerId);
    if (!dealersRepository.existsById(dealerId)) {
      throw new NotFoundException("Dealer with ID " + dealerId + " not found");
    }
    return listingsRepository.findByDealerId(dealerId).stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Creates a new listing.
   *
   * @param request the listing data
   * @return the created listing
   */
  public ListingsResponse create(ListingsRequest request) {
    logger.info("Creating listing for car model {} by dealer {}", request.carModelId(), request.dealerId());
    Listings entity = new Listings();
    applyRequest(entity, request);
    Listings saved = listingsRepository.save(entity);
    logger.info("Created listing with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Updates an existing listing.
   *
   * @param id      the ID of the listing to update
   * @param request the updated listing data
   * @return the updated listing
   * @throws NotFoundException if no listing with the given ID exists
   */
  public ListingsResponse update(Long id, ListingsRequest request) {
    logger.info("Updating listing with ID: {}", id);
    Listings existing = listingsRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Listing with ID " + id + " not found"));
    applyRequest(existing, request);
    Listings saved = listingsRepository.save(existing);
    logger.info("Updated listing with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Deletes a listing by its ID.
   *
   * @param id the ID of the listing to delete
   * @throws NotFoundException if no listing with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting listing with ID: {}", id);
    if (!listingsRepository.existsById(id)) {
      throw new NotFoundException("Listing with ID " + id + " not found");
    }
    listingsRepository.deleteById(id);
    logger.info("Deleted listing with ID: {}", id);
  }

  /**
   * Applies the fields from a request DTO to a listing entity.
   *
   * @param entity  the entity to update
   * @param request the request data
   */
  private void applyRequest(Listings entity, ListingsRequest request) {
    CarModels carModel = carModelsRepository.findById(request.carModelId())
        .orElseThrow(() -> new NotFoundException(
            "Car model with ID " + request.carModelId() + " not found"));
    Dealers dealer = dealersRepository.findById(request.dealerId())
        .orElseThrow(() -> new NotFoundException(
            "Dealer with ID " + request.dealerId() + " not found"));

    entity.setCarModel(carModel);
    entity.setDealer(dealer);
    entity.setPlateNumber(request.plateNumber());
    entity.setPrice(request.price());
    entity.setAvailability(request.availability() != null ? request.availability() : Listings.Availability.AVAILABLE);
    entity.setAvailableFrom(request.availableFrom());
    entity.setVisible(request.visible() != null ? request.visible() : true);
    entity.setColor(request.color());
    entity.setCondition(request.condition());
    entity.setMileage(request.mileage());
    entity.setImageUrl(request.imageUrl());
    entity.setSourceUrl(request.sourceUrl());
  }

  /**
   * Converts a {@link Listings} entity to a {@link ListingsResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private ListingsResponse toResponse(Listings entity) {
    CarModels cm = entity.getCarModel();
    List<ExtraFeaturesResponse> extraFeatures = cm.getExtraFeatures() == null
        ? List.of()
        : cm.getExtraFeatures().stream()
            .map(ef -> new ExtraFeaturesResponse(ef.getId(), ef.getName(), ef.getDescription()))
            .toList();

    CarModelsResponse carModelResponse = new CarModelsResponse(
        cm.getId(), cm.getBrand(), cm.getModelName(), cm.getCarType(),
        cm.getProductionYear(), cm.getPassengers(), cm.getTransmission(),
        cm.getEnergySource(), cm.getEngine(), cm.getBatteryCapacityKwh(),
        cm.getRangeKm(), cm.getRangeStandard(), cm.getTopSpeedKmh(),
        cm.getAcceleration(), extraFeatures);

    Dealers d = entity.getDealer();
    DealersResponse dealerResponse = new DealersResponse(
        d.getId(), d.getEmail(), d.getPhoneNumber(),
        d.getRole(), d.getCreatedAt(), d.getCompanyName());

    return new ListingsResponse(
        entity.getId(), carModelResponse, dealerResponse,
        entity.getPlateNumber(), entity.getPrice(), entity.getAvailability(),
        entity.getAvailableFrom(), entity.isVisible(), entity.getColor(),
        entity.getCondition(), entity.getMileage(), entity.getImageUrl(),
        entity.getSourceUrl());
  }
}
