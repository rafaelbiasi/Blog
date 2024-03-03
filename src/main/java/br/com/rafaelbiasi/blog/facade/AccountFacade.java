package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.RegistrationResponseData;

import java.util.Optional;

public interface AccountFacade {

    Optional<AccountData> findOneByEmail(String authUsername);

    Optional<AccountData> findOneByUsername(String username);

    void save(AccountData account);

    RegistrationResponseData attemptUserRegistration(AccountData account);

    RegistrationResponseData checkEmailAndUsernameExists(AccountData account);
}
