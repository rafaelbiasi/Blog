package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.domain.model.Account;
import br.com.rafaelbiasi.blog.domain.model.Role;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import br.com.rafaelbiasi.blog.domain.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    private UserDetailsService userDetailsService;
    @Mock
    private AccountService accountService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        userDetailsService = new UserDetailsServiceImpl(accountService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void loadUserByUsernameByUsername() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .password("drowssap")
                .roles(Collections.singleton(Role.builder().id(1L).name("ROLE_USER").build()))
                .build();
        when(accountService.findOneByUsername("username")).thenReturn(of(account));
        //WHEN
        UserDetails userDetailsResponse = userDetailsService.loadUserByUsername("username");
        //THEN
        UserDetails userDetails = new User("username", "drowssap",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(userDetails, userDetailsResponse);
        verify(accountService).findOneByUsername("username");
    }

    @Test
    void loadUserByUsernameByEmail() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .password("drowssap")
                .roles(Collections.singleton(Role.builder().id(1L).name("ROLE_USER").build()))
                .build();
        when(accountService.findOneByUsername("user@domain.com")).thenReturn(empty());
        when(accountService.findOneByEmail("user@domain.com")).thenReturn(of(account));
        //WHEN
        UserDetails userDetailsResponse = userDetailsService.loadUserByUsername("user@domain.com");
        //THEN
        UserDetails userDetails = new User("username", "drowssap",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(userDetails, userDetailsResponse);
        verify(accountService).findOneByUsername("user@domain.com");
        verify(accountService).findOneByEmail("user@domain.com");
    }

    @Test
    void loadUserByUsernameNotExists() {
        //GIVEN
        when(accountService.findOneByUsername("user@domain.com")).thenReturn(empty());
        when(accountService.findOneByEmail("user@domain.com")).thenReturn(empty());
        //WHEN
        Executable executable = () -> userDetailsService.loadUserByUsername("user@domain.com");
        //THEN
        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, executable);
        assertEquals("Account not found or password is not correct", usernameNotFoundException.getMessage());
        verify(accountService).findOneByUsername("user@domain.com");
        verify(accountService).findOneByEmail("user@domain.com");
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}