package br.com.rafaelbiasi.blog.domain.service.impl;

import br.com.rafaelbiasi.blog.domain.model.Account;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(final String usernameOrEmail) throws UsernameNotFoundException {
        requireNonNull(usernameOrEmail, "The Username or E-mail has a null value.");
        return findOneByUsername(usernameOrEmail)
                .or(() -> findOneByEmail(usernameOrEmail))
                .map(this::user)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found or password is not correct"));
    }

    private static User user(
            final Account account,
            final List<SimpleGrantedAuthority> grantedAuthorities
    ) {
        return new User(
                account.getUsername(),
                account.getPassword(),
                grantedAuthorities
        );
    }

    private Optional<Account> findOneByUsername(String usernameOrEmail) {
        return accountService.findOneByUsername(usernameOrEmail);
    }

    private Optional<Account> findOneByEmail(@Email String usernameOrEmail) {
        return accountService.findOneByEmail(usernameOrEmail);
    }

    private User user(final Account account) {
        return account
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(collectingAndThen(
                        toList(),
                        grantedAuthorities -> user(account, grantedAuthorities)
                ));
    }
}
