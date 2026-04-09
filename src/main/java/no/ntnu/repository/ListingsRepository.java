package no.ntnu.repository;

import java.util.List;

import no.ntnu.entity.Listings;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link Listings} entity.
 */
public interface ListingsRepository extends JpaRepository<Listings, Long> {
  List<Listings> findByDealerId(Long dealerId);
}
