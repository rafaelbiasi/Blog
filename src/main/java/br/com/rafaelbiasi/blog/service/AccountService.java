package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;

import java.util.Optional;

/**
 * Defines the contract for services that manage {@link Account} entities.
 * This interface extends {@link EntityService} to provide additional account-specific operations,
 * such as saving accounts, finding accounts by email or username, and attempting user registration.
 */
public interface AccountService extends EntityService<Account> {

    /**
     * Saves an {@link Account} entity to the persistent store.
     * This method may be used for both creating new accounts and updating existing ones.
     *
     * @param account the {@link Account} entity to save
     * @return the saved {@link Account} entity, including any generated or modified fields
     */
    Account save(Account account);

    /**
     * Retrieves an {@link Account} entity based on the given email.
     * This method returns an {@link Optional} that will be empty if no account is found with the specified email.
     *
     * @param email the email associated with the account to find
     * @return an {@link Optional} containing the found {@link Account} or empty if none found
     */
    Optional<Account> findOneByEmail(String email);

    /**
     * Retrieves an {@link Account} entity based on the given username.
     * This method returns an {@link Optional} that will be empty if no account is found with the specified username.
     *
     * @param username the username associated with the account to find
     * @return an {@link Optional} containing the found {@link Account} or empty if none found
     */
    Optional<Account> findOneByUsername(String username);

    /**
     * Attempts to register a user with the given {@link Account} information.
     * This method encapsulates the logic for validating and processing new user registrations,
     * returning a {@link RegistrationResponse} that contains information about the registration attempt,
     * such as success status, messages, and any other relevant details.
     *
     * @param account the {@link Account} entity containing the user registration information
     * @return a {@link RegistrationResponse} indicating the result of the registration attempt
     */
    RegistrationResponse attemptUserRegistration(Account account);

    RegistrationResponse checkEmailAndUsernameExists(Account account);
}
