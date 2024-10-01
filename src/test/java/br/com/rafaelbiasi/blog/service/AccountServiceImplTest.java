package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.domain.entity.Account;
import br.com.rafaelbiasi.blog.domain.microtype.RegistrationResponse;
import br.com.rafaelbiasi.blog.domain.entity.Role;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import br.com.rafaelbiasi.blog.domain.service.impl.AccountServiceImpl;
import br.com.rafaelbiasi.blog.domain.service.RoleService;
import br.com.rafaelbiasi.blog.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountService accountService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private RoleService roleService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(
                passwordEncoder,
                accountRepository,
                roleService
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getById() {
        //GIVEN
        Account account = Account.builder().build();
        when(accountRepository.findById(1L)).thenReturn(of(account));
        //WHEN
        Optional<Account> respondeAccount = accountService.findById(1);
        //THEN
        assertTrue(respondeAccount.isPresent());
        assertEquals(account, respondeAccount.get());
        verify(accountRepository).findById(1L);
    }

    @Test
    void saveNewAccountAndNotHasHole() {
        //GIVEN
        Account account = Account.builder().password("password").build();
        Role roleUser = Role.builder().id(1L).build();
        when(accountRepository.save(account)).thenReturn(account);
        when(roleService.findByName("ROLE_USER")).thenReturn(of(roleUser));
        when(passwordEncoder.encode("password")).thenReturn("drowssap");
        //WHEN
        Account accountResponse = accountService.save(account);
        //THEN
        assertEquals(account, accountResponse);
        assertEquals(Collections.singleton(roleUser), accountResponse.getRoles());
        assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleService).findByName("ROLE_USER");
    }

    @Test
    void saveNewAccountAndHasHole() {
        //GIVEN
        Account account = Account.builder().password("password").build();
        Role roleUser = Role.builder().id(1L).build();
        account.setRoles(Collections.singleton(roleUser));
        when(accountRepository.save(account)).thenReturn(account);
        when(passwordEncoder.encode("password")).thenReturn("drowssap");
        //WHEN
        Account accountResponse = accountService.save(account);
        //THEN
        assertEquals(account, accountResponse);
        assertEquals(Collections.singleton(roleUser), accountResponse.getRoles());
        assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleService, never()).findByName("ROLE_USER");
    }

    @Test
    void saveUpdateAccountAndNotHasHole() {
        //GIVEN
        Account account = Account.builder().id(1L).password("password").build();
        Role roleUser = Role.builder().id(1L).build();
        when(accountRepository.save(account)).thenReturn(account);
        when(roleService.findByName("ROLE_USER")).thenReturn(of(roleUser));
        when(passwordEncoder.encode("password")).thenReturn("drowssap");
        //WHEN
        Account accountResponse = accountService.save(account);
        //THEN
        assertEquals(account, accountResponse);
        assertTrue(accountResponse.getRoles().isEmpty());
        assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleService, never()).findByName("ROLE_USER");
    }

    @Test
    void saveUpdateAccountAndHasHole() {
        //GIVEN
        Account account = Account.builder().id(1L).password("password").build();
        Role roleUser = Role.builder().id(1L).build();
        account.setRoles(Collections.singleton(roleUser));
        when(accountRepository.save(account)).thenReturn(account);
        when(passwordEncoder.encode("password")).thenReturn("drowssap");
        //WHEN
        Account accountResponse = accountService.save(account);
        //THEN
        assertEquals(account, accountResponse);
        assertEquals(Collections.singleton(roleUser), accountResponse.getRoles());
        assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleService, never()).findByName("ROLE_USER");
    }

    @Test
    void findOneByEmail() {
        //GIVEN
        Account account = Account.builder().id(1L).build();
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(of(account));
        //WHEN
        Optional<Account> accountResponse = accountService.findOneByEmail("user@domain.com");
        //THEN
        assertTrue(accountResponse.isPresent());
        assertEquals(account, accountResponse.get());
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
    }

    @Test
    void findOneByUsername() {
        //GIVEN
        Account account = Account.builder().id(1L).build();
        when(accountRepository.findOneByUsernameIgnoreCase("username")).thenReturn(of(account));
        //WHEN
        Optional<Account> accountResponse = accountService.findOneByUsername("username");
        //THEN
        assertTrue(accountResponse.isPresent());
        assertEquals(account, accountResponse.get());
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void registerUser() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(empty());
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com")
        ).thenReturn(empty());
        //WHEN
        accountService.registerUser(account);
        //THEN
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void registerUserUsernameExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(of(account));
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(empty());
        //WHEN
        accountService.registerUser(account);
        //THEN
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void registerUserEmailExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(empty());
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(of(account));
        //WHEN
        accountService.registerUser(account);
        //THEN
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void registerUserUsernameAndEmailExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(of(account));
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(of(account));
        //WHEN
        accountService.registerUser(account);
        //THEN
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void checkEmailAndUsernameExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(empty());
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(empty());
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.checkEmailAndUsernameExists(account);
        //THEN
        assertEquals(RegistrationResponse.builder()
                .usernameExists(false)
                .emailExists(false)
                .build(), registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void checkEmailAndUsernameExistsUsernameExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(ofNullable(account));
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(empty());
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.checkEmailAndUsernameExists(account);
        //THEN
        assertEquals(RegistrationResponse.builder()
                .usernameExists(true)
                .emailExists(false)
                .build(), registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void checkEmailAndUsernameExistsEmailExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(empty());
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(ofNullable(account));
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.checkEmailAndUsernameExists(account);
        //THEN
        assertEquals(RegistrationResponse.builder()
                .usernameExists(false)
                .emailExists(true)
                .build(), registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void checkEmailAndUsernameExistsUsernameAndEmailExists() {
        //GIVEN
        Account account = Account.builder()
                .username("username")
                .email("user@domain.com")
                .build();
        when(accountRepository.findOneByUsernameIgnoreCase("username"))
                .thenReturn(ofNullable(account));
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com"))
                .thenReturn(ofNullable(account));
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.checkEmailAndUsernameExists(account);
        //THEN
        assertEquals(RegistrationResponse.builder()
                .usernameExists(true)
                .emailExists(true)
                .build(), registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void template() {
        //GIVEN
        Account account = Account.builder().id(1L).build();
        //WHEN
        accountService.delete(account);
        //THEN
        verify(accountRepository).delete(account);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}