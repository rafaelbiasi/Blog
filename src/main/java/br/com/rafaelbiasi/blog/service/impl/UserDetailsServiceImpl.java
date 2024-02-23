package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username, "Username or E-mail is null.");
        Optional<Account> accountOptional = accountService.findOneByUsername(username);
        if (accountOptional.isEmpty()) {
            accountOptional = accountService.findOneByEmail(username);
        }
        return accountOptional.map(account -> {
                    List<GrantedAuthority> grantedAuthorities = account
                            .getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList());
                    return new User(account.getEmail(), account.getPassword(), grantedAuthorities);
                })
                .orElseThrow(() -> {
                    log.warn("Account not found or password is not correct for username/email: {}", username);
                    return new UsernameNotFoundException("Account not found or password is not correct");
                });
    }
}
