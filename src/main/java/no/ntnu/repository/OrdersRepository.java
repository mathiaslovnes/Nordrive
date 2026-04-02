package no.ntnu.repository;

import no.ntnu.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link Orders} entity.
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
