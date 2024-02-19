package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.service.impl.RegistrationResponse;

import java.util.Optional;

/**
 * Defines the contract for the AccountFacade, which acts as an intermediary between the service layer and the clients,
 * encapsulating the complexity of interactions with the account services. It provides a simplified interface for
 * account operations, such as finding accounts by email, saving account data, and handling user registration processes.
 */
public interface AccountFacade {

    /**
     * Retrieves account data for a given email address. This method is typically used in authentication and
     * authorization processes to load user details based on the email which is used as the username in such contexts.
     *
     * @param authUsername the email address to search for
     * @return an {@link Optional} containing the {@link AccountData} if an account with the given email exists, or empty otherwise
     */
    Optional<AccountData> findOneByEmail(String authUsername);

    /**
     * Saves or updates the given account data in the system. This method can be used for both creating new accounts
     * and updating existing ones based on the presence of an identifier within the {@link AccountData} object.
     *
     * @param account the {@link AccountData} containing the account information to save or update
     */
    void save(AccountData account);

    /**
     * Attempts to register a new user with the given account data. This process involves validating the provided
     * account information, ensuring it meets the system's requirements for new accounts, and then creating the account
     * if all validations pass.
     *
     * @param account the {@link AccountData} containing the registration information
     * @return a {@link RegistrationResponse} containing the outcome of the registration attempt, including success status.
     */
    RegistrationResponse attemptUserRegistration(AccountData account);
}
