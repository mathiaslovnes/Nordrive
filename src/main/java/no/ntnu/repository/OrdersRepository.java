package no.ntnu.repository;

import java.util.List;

import no.ntnu.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link Orders} entity.
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {
  List<Orders> findByBuyerId(Long buyerId);

  List<Orders> findByListingId(Long listingId);

  List<Orders> findByListingDealerId(Long dealerId);
}
