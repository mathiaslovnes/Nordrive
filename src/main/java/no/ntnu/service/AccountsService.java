package no.ntnu.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import no.ntnu.dto.accounts.AccountsResponse;
import no.ntnu.dto.accounts.UpdatePasswordRequest;
import no.ntnu.entity.Accounts;
import no.ntnu.exception.BadRequestException;
import no.ntnu.exception.NotFoundException;
import no.ntnu.repository.AccountsRepository;

/**
 * Service for managing {@link Accounts} entities.
 */
@Service
public class AccountsService {
  private static final Logger logger = LoggerFactory.getLogger(AccountsService.class);

  private final AccountsRepository accountsRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountsService(AccountsRepository accountsRepository,
      PasswordEncoder passwordEncoder) {
    this.accountsRepository = accountsRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Retrieves all accounts.
   *
   * @return a list of all accounts
   */
  public List<AccountsResponse> getAll() {
    logger.debug("Retrieving all accounts");
    return accountsRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Retrieves an account by its ID.
   *
   * @param id the ID of the account
   * @return the account
   * @throws NotFoundException if no account with the given ID exists
   */
  public AccountsResponse getById(Long id) {
    logger.debug("Retrieving account with ID: {}", id);
    return accountsRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException(
            "Account with ID " + id + " not found"));
  }

  /**
   * Soft-deletes an account by its ID.
   *
   * @param id the ID of the account to delete
   * @throws NotFoundException if no account with the given ID exists
   */
  public void deleteById(Long id) {
    logger.info("Deleting account with ID: {}", id);
    Accounts existing = accountsRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Account with ID " + id + " not found"));
    existing.setDeleted(true);
    accountsRepository.save(existing);
    logger.info("Deleted account with ID: {}", id);
  }

  /**
   * Updates the password for an account.
   *
   * @param id      the ID of the account
   * @param request the password update request
   * @throws NotFoundException if no account with the given ID exists
   * @throws IllegalArgumentException if the old password does not match
   */
  public void updatePassword(Long id, UpdatePasswordRequest request) {
    logger.info("Updating password for account with ID: {}", id);
    Accounts existing = accountsRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Account with ID " + id + " not found"));
    if (!passwordEncoder.matches(request.oldPassword(), existing.getPassword())) {
      logger.warn("Password update failed for account with ID: {}: incorrect old password", id);
      throw new BadRequestException("Old password is incorrect");
    }
    existing.setPassword(passwordEncoder.encode(request.newPassword()));
    accountsRepository.save(existing);
    logger.info("Updated password for account with ID: {}", id);
  }

  /**
   * Converts an {@link Accounts} entity to an {@link AccountsResponse} DTO.
   *
   * @param entity the entity to convert
   * @return the response DTO
   */
  private AccountsResponse toResponse(Accounts entity) {
    return new AccountsResponse(
        entity.getId(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        entity.getRole(),
        entity.getCreatedAt());
  }
}
