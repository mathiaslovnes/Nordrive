package no.ntnu.repository;

import no.ntnu.entity.CarModels;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link CarModels} entity.
 */
public interface CarModelsRepository extends JpaRepository<CarModels, Long> {
}
