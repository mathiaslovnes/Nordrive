package no.ntnu.repository;

import no.ntnu.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link Users} entity.
 */
public interface UsersRepository extends JpaRepository<Users, Long> {
}
