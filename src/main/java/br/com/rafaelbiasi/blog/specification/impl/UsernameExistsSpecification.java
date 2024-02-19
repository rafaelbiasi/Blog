package br.com.rafaelbiasi.blog.specification.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.repository.AccountRepository;
import br.com.rafaelbiasi.blog.specification.Specification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UsernameExistsSpecification implements Specification<Account> {

    private final AccountRepository accountRepository;

    @Override
    public boolean isSatisfiedBy(Account account) {
        Objects.requireNonNull(account, "Account is null.");
        return accountRepository.findOneByUsernameIgnoreCase(account.getUsername()).isPresent();
    }
}
