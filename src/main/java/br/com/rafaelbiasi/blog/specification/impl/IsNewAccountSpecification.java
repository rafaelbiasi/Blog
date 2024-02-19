package br.com.rafaelbiasi.blog.specification.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.specification.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsNewAccountSpecification implements Specification<Account> {
    @Override
    public boolean isSatisfiedBy(Account account) {
        return account.getId() == null;
    }
}
