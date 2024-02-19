package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Defines the repository interface for {@link Account} entities.
 * This interface extends {@link BlogRepository} to inherit basic CRUD operations,
 * and adds additional methods specific to {@link Account} entities, such as finding an account by email or username,
 * with case-insensitive search.
 * The {@link Repository} annotation indicates that it is a Spring-managed repository component.
 */
@Repository
public interface AccountRepository extends BlogRepository<Account> {

    /**
     * Retrieves an {@link Account} by its email in a case-insensitive manner.
     * This method is useful in scenarios where email addresses are treated as case-insensitive identifiers.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the matching {@link Account} if found, or empty otherwise
     */
    Optional<Account> findOneByEmailIgnoreCase(String email);

    /**
     * Retrieves an {@link Account} by its username in a case-insensitive manner.
     * This method allows for user identification without regard to case sensitivity in usernames.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the matching {@link Account} if found, or empty otherwise
     */
    Optional<Account> findOneByUsernameIgnoreCase(String username);
}
