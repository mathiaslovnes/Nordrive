package no.ntnu.repository;

import no.ntnu.entity.Dealers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link Dealers} entity.
 */
public interface DealersRepository extends JpaRepository<Dealers, Long> {
}
