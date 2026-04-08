package no.ntnu.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import no.ntnu.dto.extrafeatures.ExtraFeaturesRequest;
import no.ntnu.dto.extrafeatures.ExtraFeaturesResponse;
import no.ntnu.entity.ExtraFeatures;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.ExtraFeaturesRepository;

/**
 * Service for managing {@link ExtraFeatures} entities.
 */
@Service
public class ExtraFeaturesService {
  private static final Logger logger = LoggerFactory.getLogger(ExtraFeaturesService.class);

  private final ExtraFeaturesRepository extraFeaturesRepository;

  public ExtraFeaturesService(ExtraFeaturesRepository extraFeaturesRepository) {
    this.extraFeaturesRepository = extraFeaturesRepository;
  }

  /**
   * Retrieves all extra features.
   *
   * @return a list of all extra features
   */
  public List<ExtraFeaturesResponse> getAll() {
    logger.debug("Retrieving all extra features");
    return extraFeaturesRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves an extra feature by its ID.
   *
   * @param id the ID of the extra feature
   * @return the extra feature
   * @throws NotFoundException if no extra feature with the given ID exists
   */
  public ExtraFeaturesResponse getById(Long id) {
    logger.debug("Retrieving extra feature with ID: {}", id);
    return extraFeaturesRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "Extra feature with ID " + id + " not found"));
  }

  /**
   * Creates a new extra feature.
   *
   * @param request the extra feature data
   * @return the created extra feature
   */
  public ExtraFeaturesResponse create(ExtraFeaturesRequest request) {
    logger.info("Creating extra feature with name: {}", request.name());
    ExtraFeatures entity = new ExtraFeatures();
    entity.setName(request.name());
    entity.setDescription(request.description());
    ExtraFeatures saved = extraFeaturesRepository.save(entity);
    logger.info("Created extra feature with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Updates an existing extra feature.
   *
   * @param id      the ID of the extra feature to update
   * @param request the updated extra feature data
   * @return the updated extra feature
   * @throws NotFoundException if no extra feature with the given ID exists
   */
  public ExtraFeaturesResponse update(Long id, ExtraFeaturesRequest request) {
    logger.info("Updating extra feature with ID: {}", id);
    ExtraFeatures existing = extraFeaturesRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Extra feature with ID " + id + " not found"));
    existing.setName(request.name());
    existing.setDescription(request.description());
    ExtraFeatures saved = extraFeaturesRepository.save(existing);
    logger.info("Updated extra feature with ID: {}", saved.getId());
    return toResponse(saved);
  }

  /**
   * Deletes an extra feature by its ID.
   *
   * @param id the ID of the extra feature to delete
   * @throws NotFoundException if no extra feature with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting extra feature with ID: {}", id);
    if (!extraFeaturesRepository.existsById(id)) {
      throw new NotFoundException(
          "Extra feature with ID " + id + " not found");
    }
    extraFeaturesRepository.deleteById(id);
    logger.info("Deleted extra feature with ID: {}", id);
  }

  /**
   * Converts an {@link ExtraFeatures} entity to an {@link ExtraFeaturesResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private ExtraFeaturesResponse toResponse(ExtraFeatures entity) {
    return new ExtraFeaturesResponse(entity.getId(), entity.getName(), entity.getDescription());
  }
}
