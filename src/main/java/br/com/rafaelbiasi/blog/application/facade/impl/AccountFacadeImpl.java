package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.facade.AccountFacade;
import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.application.mapper.AccountMapper;
import br.com.rafaelbiasi.blog.domain.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Override
    public Optional<AccountData> findOneByEmail(final String email) {
        requireNonNull(email, "The E-mail has a null value.");
        return accountService.findOneByEmail(email)
                .map(accountMapper::accountToAccountData);
    }

    @Override
    public Optional<AccountData> findOneByUsername(final String username) {
        requireNonNull(username, "The Username has a null value.");
        return accountService.findOneByUsername(username)
                .map(accountMapper::accountToAccountData);
    }

    @Override
    public void save(final AccountData accountData) {
        requireNonNull(accountData, "The Account has a null value.");
        accountService.save(accountMapper.accountDataToAccount(accountData));
    }

    @Override
    public void registerUser(final AccountData accountData) {
        requireNonNull(accountData, "The Account has a null value.");
        accountService.registerUser(accountMapper.accountDataToAccount(accountData));
    }

    @Override
    public RegistrationResponse checkEmailAndUsernameExists(final AccountData accountData) {
        requireNonNull(accountData, "The Account has a null value.");
        return accountService.checkEmailAndUsernameExists(accountMapper.accountDataToAccount(accountData));
    }
}
