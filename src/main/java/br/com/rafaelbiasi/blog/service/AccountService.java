package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;

import java.util.Optional;

public interface AccountService extends EntityService<Account> {

    Optional<Account> findOneByEmail(String email);

    Optional<Account> findOneByUsername(String username);

    RegistrationResponse attemptUserRegistration(Account account);

    RegistrationResponse checkEmailAndUsernameExists(Account account);
}
