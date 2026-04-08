package no.ntnu.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import no.ntnu.dto.carmodels.CarModelsResponse;
import no.ntnu.dto.dealers.DealersResponse;
import no.ntnu.dto.extrafeatures.ExtraFeaturesResponse;
import no.ntnu.dto.listings.ListingsResponse;
import no.ntnu.dto.orders.OrdersRequest;
import no.ntnu.dto.orders.OrdersResponse;
import no.ntnu.dto.users.UsersResponse;
import no.ntnu.entity.CarModels;
import no.ntnu.entity.Dealers;
import no.ntnu.entity.Listings;
import no.ntnu.entity.Orders;
import no.ntnu.entity.Users;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.DealersRepository;
import no.ntnu.repository.ListingsRepository;
import no.ntnu.repository.OrdersRepository;
import no.ntnu.repository.UsersRepository;

/**
 * Service for managing {@link Orders} entities.
 */
@Service
public class OrdersService {
  private static final Logger logger = LoggerFactory.getLogger(OrdersService.class);

  private final OrdersRepository ordersRepository;
  private final UsersRepository usersRepository;
  private final ListingsRepository listingsRepository;
  private final DealersRepository dealersRepository;

  public OrdersService(OrdersRepository ordersRepository,
      UsersRepository usersRepository,
      ListingsRepository listingsRepository,
      DealersRepository dealersRepository) {
    this.ordersRepository = ordersRepository;
    this.usersRepository = usersRepository;
    this.listingsRepository = listingsRepository;
    this.dealersRepository = dealersRepository;
  }

  /**
   * Retrieves all orders.
   *
   * @return a list of all orders
   */
  public List<OrdersResponse> getAll() {
    logger.debug("Retrieving all orders");
    return ordersRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves an order by its ID.
   *
   * @param id the ID of the order
   * @return the order
   * @throws NotFoundException if no order with the given ID exists
   */
  public OrdersResponse getById(Long id) {
    logger.debug("Retrieving order with ID: {}", id);
    return ordersRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "Order with ID " + id + " not found"));
  }

  /**
   * Retrieves all orders for a given buyer.
   *
   * @param userId the ID of the buyer
   * @return a list of orders for the buyer
   * @throws NotFoundException if no user with the given ID exists
   */
  public List<OrdersResponse> getByUserId(Long userId) {
    logger.debug("Retrieving orders for user with ID: {}", userId);
    if (!usersRepository.existsById(userId)) {
      throw new NotFoundException("User with ID " + userId + " not found");
    }
    return ordersRepository.findByBuyerId(userId).stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves all orders for a given listing.
   *
   * @param listingId the ID of the listing
   * @return a list of orders for the listing
   * @throws NotFoundException if no listing with the given ID exists
   */
  public List<OrdersResponse> getByListingId(Long listingId) {
    logger.debug("Retrieving orders for listing with ID: {}", listingId);
    if (!listingsRepository.existsById(listingId)) {
      throw new NotFoundException("Listing with ID " + listingId + " not found");
    }
    return ordersRepository.findByListingId(listingId).stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Creates a new order.
   *
   * @param request the order data
   * @return the created order
   */
  public OrdersResponse create(OrdersRequest request) {
    logger.info("Creating order for buyer {} on listing {}", request.buyerId(), request.listingId());
    Orders entity = new Orders();
    applyRequest(entity, request);
    Orders saved = ordersRepository.save(entity);
    logger.info("Created order with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Updates the status of an existing order.
   *
   * @param id     the ID of the order
   * @param status the new status
   * @return the updated order
   * @throws NotFoundException if no order with the given ID exists
   */
  public OrdersResponse updateStatus(Long id, Orders.Status status) {
    logger.info("Updating status of order {} to {}", id, status);
    Orders existing = ordersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Order with ID " + id + " not found"));
    existing.setStatus(status);
    Orders saved = ordersRepository.save(existing);
    logger.info("Updated status of order {} to {}", id, status);
    return toResponse(saved);
  }

  /**
   * Retrieves all orders for a given dealer.
   *
   * @param dealerId the ID of the dealer
   * @return a list of orders for the dealer
   */
  public List<OrdersResponse> getByDealerId(Long dealerId) {
    logger.debug("Retrieving orders for dealer with ID: {}", dealerId);
    if (!dealersRepository.existsById(dealerId)) {
      throw new NotFoundException("Dealer with ID " + dealerId + " not found");
    }
    return ordersRepository.findByListingDealerId(dealerId).stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Soft-deletes an order by its ID.
   *
   * @param id the ID of the order to delete
   * @throws NotFoundException if no order with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting order with ID: {}", id);
    Orders existing = ordersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Order with ID " + id + " not found"));
    existing.setDeleted(true);
    ordersRepository.save(existing);
    logger.info("Deleted order with ID: {}", id);
  }

  /**
   * Applies the fields from a request DTO to an order entity.
   *
   * @param entity  the entity to update
   * @param request the request data
   */
  private void applyRequest(Orders entity, OrdersRequest request) {
    Users buyer = usersRepository.findById(request.buyerId())
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + request.buyerId() + " not found"));
    Listings listing = listingsRepository.findById(request.listingId())
        .orElseThrow(() -> new NotFoundException(
            "Listing with ID " + request.listingId() + " not found"));
    entity.setBuyer(buyer);
    entity.setListing(listing);
    entity.setPurchasePrice(listing.getPrice());
  }

  /**
   * Converts an {@link Orders} entity to an {@link OrdersResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private OrdersResponse toResponse(Orders entity) {
    return new OrdersResponse(
        entity.getId(),
        toUsersResponse(entity.getBuyer()),
        toListingsResponse(entity.getListing()),
        entity.getPurchasePrice(),
        entity.getOrderedAt(),
        entity.getStatus());
  }

  private UsersResponse toUsersResponse(Users user) {
    Set<Long> favoriteListingIds = user.getFavoriteListings() == null
        ? Set.of()
        : user.getFavoriteListings().stream()
            .map(Listings::getId)
            .collect(Collectors.toSet());

    return new UsersResponse(
        user.getId(), user.getEmail(), user.getPhoneNumber(),
        user.getRole(), user.getCreatedAt(), user.getFirstName(),
        user.getLastName(), favoriteListingIds);
  }

  private ListingsResponse toListingsResponse(Listings listing) {
    CarModels cm = listing.getCarModel();
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

    Dealers d = listing.getDealer();
    DealersResponse dealerResponse = new DealersResponse(
        d.getId(), d.getEmail(), d.getPhoneNumber(),
        d.getRole(), d.getCreatedAt(), d.getCompanyName());

    return new ListingsResponse(
        listing.getId(), carModelResponse, dealerResponse,
        listing.getPlateNumber(), listing.getPrice(), listing.getAvailability(),
        listing.getAvailableFrom(), listing.isVisible(), listing.getColor(),
        listing.getCondition(), listing.getMileage(), listing.getImageUrl(),
        listing.getSourceUrl());
  }
}
