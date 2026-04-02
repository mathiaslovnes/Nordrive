package no.ntnu.repository;

import no.ntnu.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link Accounts} entity.
 */
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
  Accounts findByEmail(String email);
}
