package br.com.rafaelbiasi.blog.domain.service.impl;

import br.com.rafaelbiasi.blog.domain.microtype.RegistrationResponse;
import br.com.rafaelbiasi.blog.domain.entity.Account;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import br.com.rafaelbiasi.blog.domain.service.RoleService;
import br.com.rafaelbiasi.blog.infrastructure.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleService roleService;

    @Override
    public Optional<Account> findById(final long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void delete(final Account account) {
        accountRepository.delete(account);
    }

    @Override
    public Account save(final Account account) {
        requireNonNull(account, "The Account has a null value.");
        of(account).filter(Account::isNew)
                .filter(Account::hasNoHoles)
                .ifPresent(acc -> roleService
                        .findByName("ROLE_USER")
                        .map(Collections::singleton)
                        .ifPresent(acc::setRoles)
                );
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findOneByEmail(final String email) {
        requireNonNull(email, "The E-mail has a null value.");
        return accountRepository.findOneByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Account> findOneByUsername(final String username) {
        requireNonNull(username, "The Username has a null value.");
        return accountRepository.findOneByUsernameIgnoreCase(username);
    }

    @Override
    public void registerUser(final Account account) {
        requireNonNull(account, "The Account has a null value.");
        save(account);
    }

    @Override
    public RegistrationResponse checkEmailAndUsernameExists(final Account account) {
        return RegistrationResponse.builder()
                .emailExists(isEmailExists(account))
                .usernameExists(isUsernameExists(account))
                .build();
    }

    private boolean isUsernameExists(final Account account) {
        return accountRepository.findOneByUsernameIgnoreCase(account.getUsername()).isPresent();
    }

    private boolean isEmailExists(final Account account) {
        return accountRepository.findOneByEmailIgnoreCase(account.getEmail()).isPresent();
    }
}
