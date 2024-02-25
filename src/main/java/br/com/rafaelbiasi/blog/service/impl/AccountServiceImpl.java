package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.repository.AccountRepository;
import br.com.rafaelbiasi.blog.repository.RoleRepository;
import br.com.rafaelbiasi.blog.service.AccountService;
import br.com.rafaelbiasi.blog.specification.impl.EmailExistsSpecification;
import br.com.rafaelbiasi.blog.specification.impl.HasRolesSpecification;
import br.com.rafaelbiasi.blog.specification.impl.IsNewAccountSpecification;
import br.com.rafaelbiasi.blog.specification.impl.UsernameExistsSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UsernameExistsSpecification usernameExistsSpec;
    private final EmailExistsSpecification emailExistsSpec;
    private final IsNewAccountSpecification isNewAccountSpec;
    private final HasRolesSpecification hasRolesSpec;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        Objects.requireNonNull(account, "Account is null.");
        if (isNewAccountSpec.andNot(hasRolesSpec).isSatisfiedBy(account)) {
            roleRepository.findByName("ROLE_USER").map(Collections::singleton).ifPresent(account::setRoles);
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findOneByEmail(String email) {
        Objects.requireNonNull(email, "Email is null.");
        return accountRepository.findOneByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Account> findOneByUsername(String username) {
        Objects.requireNonNull(username, "Username is null.");
        return accountRepository.findOneByUsernameIgnoreCase(username);
    }

    @Override
    public RegistrationResponse attemptUserRegistration(Account account) {
        Objects.requireNonNull(account, "Account is null.");
        RegistrationResponse registrationResponse = checkEmailAndUsernameExists(account);
        if (registrationResponse.success()) {
            save(account);
        }
        return registrationResponse;
    }

    @Override
    public RegistrationResponse checkEmailAndUsernameExists(Account account) {
        return RegistrationResponse.builder()
                .emailExists(emailExistsSpec.isSatisfiedBy(account))
                .usernameExists(usernameExistsSpec.isSatisfiedBy(account))
                .build();
    }
}
