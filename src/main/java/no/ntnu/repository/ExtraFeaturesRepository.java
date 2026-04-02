package no.ntnu.repository;

import no.ntnu.entity.ExtraFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link ExtraFeatures} entity.
 */
public interface ExtraFeaturesRepository extends JpaRepository<ExtraFeatures, Long> {
}
