package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Account;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        requireNonNull(username, "The Username or E-mail has a null value.");
        Optional<Account> accountOptional = accountService.findOneByUsername(username);
        if (accountOptional.isEmpty()) {
            accountOptional = accountService.findOneByEmail(username);
        }
        return accountOptional.map(this::user)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found or password is not correct"));
    }

    private User user(Account account) {
        return account
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(collectingAndThen(toList(), grantedAuthorities -> user(account, grantedAuthorities)));
    }

    private static User user(Account account, List<SimpleGrantedAuthority> grantedAuthorities) {
        return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
    }
}
