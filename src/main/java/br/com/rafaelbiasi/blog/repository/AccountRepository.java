package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BlogRepository<Account> {

    Optional<Account> findOneByEmailIgnoreCase(String email);

    Optional<Account> findOneByUsernameIgnoreCase(String username);
}
