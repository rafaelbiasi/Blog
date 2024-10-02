package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.domain.model.RegistrationResponse;

import java.util.Optional;

public interface AccountFacade {

    Optional<AccountData> findOneByEmail(final String email);

    Optional<AccountData> findOneByUsername(final String username);

    void save(final AccountData account);

    void registerUser(final AccountData account);

    RegistrationResponse checkEmailAndUsernameExists(final AccountData account);
}
