package no.ntnu.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import no.ntnu.dto.users.UsersRequest;
import no.ntnu.dto.users.UsersResponse;
import no.ntnu.entity.Listings;
import no.ntnu.entity.Users;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.ListingsRepository;
import no.ntnu.repository.UsersRepository;

/**
 * Service for managing {@link Users} entities.
 */
@Service
public class UsersService {
  private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

  private final UsersRepository usersRepository;
  private final ListingsRepository listingsRepository;
  private final PasswordEncoder passwordEncoder;

  public UsersService(UsersRepository usersRepository,
      ListingsRepository listingsRepository,
      PasswordEncoder passwordEncoder) {
    this.usersRepository = usersRepository;
    this.listingsRepository = listingsRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Retrieves all users.
   *
   * @return a list of all users
   */
  public List<UsersResponse> getAll() {
    logger.debug("Retrieving all users");
    return usersRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves a user by its ID.
   *
   * @param id the ID of the user
   * @return the user
   * @throws NotFoundException if no user with the given ID exists
   */
  public UsersResponse getById(Long id) {
    logger.debug("Retrieving user with ID: {}", id);
    return usersRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + id + " not found"));
  }

  /**
   * Creates a new user.
   *
   * @param request the user data
   * @return the created user
   */
  public UsersResponse create(UsersRequest request) {
    logger.info("Creating user with email: {}", request.email());
    Users entity = new Users();
    applyRequest(entity, request);
    Users saved = usersRepository.save(entity);
    logger.info("Created user with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Updates an existing user.
   *
   * @param id      the ID of the user to update
   * @param request the updated user data
   * @return the updated user
   * @throws NotFoundException if no user with the given ID exists
   */
  public UsersResponse update(Long id, UsersRequest request) {
    logger.info("Updating user with ID: {}", id);
    Users existing = usersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + id + " not found"));
    applyRequest(existing, request);
    Users saved = usersRepository.save(existing);
    logger.info("Updated user with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Soft-deletes a user by its ID.
   *
   * @param id the ID of the user to delete
   * @throws NotFoundException if no user with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting user with ID: {}", id);
    Users existing = usersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + id + " not found"));
    existing.setDeleted(true);
    usersRepository.save(existing);
    logger.info("Deleted user with ID: {}", id);
  }

  /**
   * Retrieves the favorite listing IDs for a user.
   *
   * @param id the ID of the user
   * @return a set of favorite listing IDs
   * @throws NotFoundException if no user with the given ID exists
   */
  public Set<Long> getFavorites(Long id) {
    logger.debug("Retrieving favorites for user with ID: {}", id);
    Users user = usersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + id + " not found"));
    return user.getFavoriteListings() == null
        ? Set.of()
        : user.getFavoriteListings().stream()
            .map(Listings::getId)
            .collect(Collectors.toSet());
  }

  /**
   * Adds a listing to a user's favorites.
   *
   * @param userId    the ID of the user
   * @param listingId the ID of the listing to add
   * @throws NotFoundException if the user or listing does not exist
   */
  public void addFavorite(Long userId, Long listingId) {
    logger.info("Adding listing {} to favorites for user {}", listingId, userId);
    Users user = usersRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + userId + " not found"));
    Listings listing = listingsRepository.findById(listingId)
        .orElseThrow(() -> new NotFoundException(
            "Listing with ID " + listingId + " not found"));
    if (user.getFavoriteListings() == null) {
      user.setFavoriteListings(new HashSet<>());
    }
    user.getFavoriteListings().add(listing);
    usersRepository.save(user);
    logger.info("Added listing {} to favorites for user {}", listingId, userId);
  }

  /**
   * Removes a listing from a user's favorites.
   *
   * @param userId    the ID of the user
   * @param listingId the ID of the listing to remove
   * @throws NotFoundException if the user or listing does not exist
   */
  public void removeFavorite(Long userId, Long listingId) {
    logger.info("Removing listing {} from favorites for user {}", listingId, userId);
    Users user = usersRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(
            "User with ID " + userId + " not found"));
    Listings listing = listingsRepository.findById(listingId)
        .orElseThrow(() -> new NotFoundException(
            "Listing with ID " + listingId + " not found"));
    if (user.getFavoriteListings() != null) {
      user.getFavoriteListings().remove(listing);
      usersRepository.save(user);
    }
    logger.info("Removed listing {} from favorites for user {}", listingId, userId);
  }

  /**
   * Applies the fields from a request DTO to a user entity.
   *
   * @param entity  the entity to update
   * @param request the request data
   */
  private void applyRequest(Users entity, UsersRequest request) {
    entity.setEmail(request.email());
    entity.setPhoneNumber(request.phoneNumber());
    entity.setPassword(passwordEncoder.encode(request.password()));
    entity.setFirstName(request.firstName());
    entity.setLastName(request.lastName());
  }

  /**
   * Converts a {@link Users} entity to a {@link UsersResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private UsersResponse toResponse(Users entity) {
    Set<Long> favoriteListingIds = entity.getFavoriteListings() == null
        ? Set.of()
        : entity.getFavoriteListings().stream()
            .map(Listings::getId)
            .collect(Collectors.toSet());

    return new UsersResponse(
        entity.getId(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        entity.getRole(),
        entity.getCreatedAt(),
        entity.getFirstName(),
        entity.getLastName(),
        favoriteListingIds);
  }
}
