package no.ntnu.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import no.ntnu.dto.dealers.DealersCreateRequest;
import no.ntnu.dto.dealers.DealersResponse;
import no.ntnu.dto.dealers.DealersUpdateRequest;
import no.ntnu.entity.Dealers;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.DealersRepository;

/**
 * Service for managing {@link Dealers} entities.
 */
@Service
public class DealersService {
  private static final Logger logger = LoggerFactory.getLogger(DealersService.class);

  private final DealersRepository dealersRepository;
  private final PasswordEncoder passwordEncoder;

  public DealersService(DealersRepository dealersRepository,
      PasswordEncoder passwordEncoder) {
    this.dealersRepository = dealersRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Retrieves all dealers.
   *
   * @return a list of all dealers
   */
  public List<DealersResponse> getAll() {
    logger.debug("Retrieving all dealers");
    return dealersRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves a dealer by its ID.
   *
   * @param id the ID of the dealer
   * @return the dealer
   * @throws NotFoundException if no dealer with the given ID exists
   */
  public DealersResponse getById(Long id) {
    logger.debug("Retrieving dealer with ID: {}", id);
    return dealersRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "Dealer with ID " + id + " not found"));
  }

  /**
   * Creates a new dealer.
   *
   * @param request the dealer data
   * @return the created dealer
   */
  public DealersResponse create(DealersCreateRequest request) {
    logger.info("Creating dealer with email: {}", request.email());
    Dealers entity = new Dealers();
    applyRequest(entity, request);
    Dealers saved = dealersRepository.save(entity);
    logger.info("Created dealer with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Updates an existing dealer.
   *
   * @param id      the ID of the dealer to update
   * @param request the updated dealer data
   * @return the updated dealer
   * @throws NotFoundException if no dealer with the given ID exists
   */
  public DealersResponse update(Long id, DealersUpdateRequest request) {
    logger.info("Updating dealer with ID: {}", id);
    Dealers existing = dealersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Dealer with ID " + id + " not found"));
    applyRequest(existing, request);
    Dealers saved = dealersRepository.save(existing);
    logger.info("Updated dealer with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Soft-deletes a dealer by its ID.
   *
   * @param id the ID of the dealer to delete
   * @throws NotFoundException if no dealer with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting dealer with ID: {}", id);
    Dealers existing = dealersRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Dealer with ID " + id + " not found"));
    existing.setDeleted(true);
    dealersRepository.save(existing);
    logger.info("Deleted dealer with ID: {}", id);
  }

  /**
   * Applies the fields from a request DTO to a dealer entity.
   *
   * @param entity  the entity to update
   * @param request the request data
   */
  private void applyRequest(Dealers entity, DealersCreateRequest request) {
    entity.setEmail(request.email());
    entity.setPhoneNumber(request.phoneNumber());
    entity.setPassword(passwordEncoder.encode(request.password()));
    entity.setCompanyName(request.companyName());
  }

  /**
   * Applies the fields from an update request DTO to a dealer entity.
   *
   * @param entity  the entity to update
   * @param request the request data
   */
  private void applyRequest(Dealers entity, DealersUpdateRequest request) {
    entity.setEmail(request.email());
    entity.setPhoneNumber(request.phoneNumber());
    entity.setCompanyName(request.companyName());
  }

  /**
   * Converts a {@link Dealers} entity to a {@link DealersResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private DealersResponse toResponse(Dealers entity) {
    return new DealersResponse(
        entity.getId(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        entity.getRole(),
        entity.getCreatedAt(),
        entity.getCompanyName());
  }
}
