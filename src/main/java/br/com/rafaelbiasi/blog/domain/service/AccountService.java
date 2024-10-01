package br.com.rafaelbiasi.blog.domain.service;

import br.com.rafaelbiasi.blog.domain.entity.Account;
import br.com.rafaelbiasi.blog.domain.microtype.RegistrationResponse;

import java.util.Optional;

public interface AccountService extends EntityService<Account> {

    Optional<Account> findOneByEmail(final String email);

    Optional<Account> findOneByUsername(final String username);

    void registerUser(final Account account);

    RegistrationResponse checkEmailAndUsernameExists(final Account account);
}
