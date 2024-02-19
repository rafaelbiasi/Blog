package br.com.rafaelbiasi.blog.facade.impl;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.facade.AccountFacade;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.service.AccountService;
import br.com.rafaelbiasi.blog.service.impl.RegistrationResponse;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final Transformer<Account, AccountData> accountDataTransformer;
    private final Transformer<AccountData, Account> accountTransformer;

    public AccountFacadeImpl(
            AccountService accountService,
            @Qualifier("accountDataTransformer") Transformer<Account, AccountData> accountDataTransformer,
            @Qualifier("accountTransformer") Transformer<AccountData, Account> accountTransformer
    ) {
        this.accountService = accountService;
        this.accountDataTransformer = accountDataTransformer;
        this.accountTransformer = accountTransformer;
    }

    @Override
    public Optional<AccountData> findOneByEmail(String email) {
        Objects.requireNonNull(email, "E-mail is null.");
        return accountService.findOneByEmail(email)
                .map(accountDataTransformer::convert);
    }

    @Override
    public void save(AccountData account) {
        Objects.requireNonNull(account, "AccountData is null.");
        accountService.save(accountTransformer.convert(account));
    }

    @Override
    public RegistrationResponse attemptUserRegistration(AccountData account) {
        Objects.requireNonNull(account, "AccountData is null.");
        return accountService.attemptUserRegistration(accountTransformer.convert(account));
    }
}
