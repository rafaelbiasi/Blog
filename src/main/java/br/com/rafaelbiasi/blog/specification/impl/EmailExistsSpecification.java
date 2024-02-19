package br.com.rafaelbiasi.blog.specification.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.repository.AccountRepository;
import br.com.rafaelbiasi.blog.specification.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmailExistsSpecification implements Specification<Account> {
    private final AccountRepository accountRepository;

    public EmailExistsSpecification(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean isSatisfiedBy(Account account) {
        return accountRepository.findOneByEmailIgnoreCase(account.getEmail()).isPresent();
    }
}
