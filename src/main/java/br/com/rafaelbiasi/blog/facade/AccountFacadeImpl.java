package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.RegistrationResponseData;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.service.AccountService;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
@Transactional
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final Transformer<Account, AccountData> accountDataTransformer;
    private final Transformer<AccountData, Account> accountTransformer;

    public AccountFacadeImpl(
            AccountService accountService,
            @Qualifier("accountDataTransformer") Transformer<Account, AccountData> accountDataTransformer,
            @Qualifier("accountTransformer") Transformer<AccountData, Account> accountTransformer) {
        this.accountService = accountService;
        this.accountDataTransformer = accountDataTransformer;
        this.accountTransformer = accountTransformer;
    }

    @Override
    public Optional<AccountData> findOneByEmail(String email) {
        requireNonNull(email, "E-mail is null.");
        return accountService.findOneByEmail(email)
                .map(accountDataTransformer::convert);
    }

    @Override
    public Optional<AccountData> findOneByUsername(String username) {
        requireNonNull(username, "Username is null.");
        return accountService.findOneByUsername(username)
                .map(accountDataTransformer::convert);
    }

    @Override
    public void save(AccountData account) {
        requireNonNull(account, "AccountData is null.");
        accountService.save(accountTransformer.convert(account));
    }

    @Override
    public RegistrationResponseData attemptUserRegistration(AccountData account) {
        requireNonNull(account, "AccountData is null.");
        Account convert = accountTransformer.convert(account);
        RegistrationResponse registrationResponse = accountService.attemptUserRegistration(convert);
        return RegistrationResponseData.builder()
                .emailExists(registrationResponse.emailExists())
                .usernameExists(registrationResponse.usernameExists())
                .build();
    }

    @Override
    public RegistrationResponseData checkEmailAndUsernameExists(AccountData account) {
        requireNonNull(account, "AccountData is null.");
        RegistrationResponse registrationResponse = accountService.checkEmailAndUsernameExists(accountTransformer.convert(account));
        return RegistrationResponseData.builder()
                .emailExists(registrationResponse.emailExists())
                .usernameExists(registrationResponse.usernameExists())
                .build();
    }
}
