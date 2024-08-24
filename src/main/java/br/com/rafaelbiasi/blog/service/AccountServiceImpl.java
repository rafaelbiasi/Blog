package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.repository.AccountRepository;
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
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        requireNonNull(account, "Account is null.");
        of(account)
                .filter(Account::isNew)
                .filter(Account::hasNoHoles)
                .ifPresent(acc -> roleService
                        .findByName("ROLE_GUEST")
                        .map(Collections::singleton)
                        .ifPresent(acc::setRoles)
                );
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findOneByEmail(String email) {
        requireNonNull(email, "Email is null.");
        return accountRepository.findOneByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Account> findOneByUsername(String username) {
        requireNonNull(username, "Username is null.");
        return accountRepository.findOneByUsernameIgnoreCase(username);
    }

    @Override
    public RegistrationResponse attemptUserRegistration(Account account) {
        requireNonNull(account, "Account is null.");
        Optional<RegistrationResponse> registrationResponse = of(checkEmailAndUsernameExists(account));
        registrationResponse
                .filter(RegistrationResponse::success)
                .ifPresent(response -> save(account));
        return registrationResponse.get();
    }

    @Override
    public RegistrationResponse checkEmailAndUsernameExists(Account account) {
        return RegistrationResponse.builder()
                .emailExists(accountRepository.findOneByEmailIgnoreCase(account.getEmail()).isPresent())
                .usernameExists(accountRepository.findOneByUsernameIgnoreCase(account.getUsername()).isPresent())
                .build();
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
