package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.repository.AccountRepository;
import br.com.rafaelbiasi.blog.repository.RoleRepository;
import br.com.rafaelbiasi.blog.service.AccountService;
import br.com.rafaelbiasi.blog.specification.impl.EmailExistsSpecification;
import br.com.rafaelbiasi.blog.specification.impl.HasRolesSpecification;
import br.com.rafaelbiasi.blog.specification.impl.IsNewAccountSpecification;
import br.com.rafaelbiasi.blog.specification.impl.UsernameExistsSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountService accountService;
    private AutoCloseable closeable;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        UsernameExistsSpecification usernameExistsSpec = new UsernameExistsSpecification(accountRepository);
        EmailExistsSpecification emailExistsSpec = new EmailExistsSpecification(accountRepository);
        HasRolesSpecification hasRolesSpec = new HasRolesSpecification();
        IsNewAccountSpecification isNewAccountSpec = new IsNewAccountSpecification();
        accountService = new AccountServiceImpl(
                usernameExistsSpec,
                emailExistsSpec,
                isNewAccountSpec,
                hasRolesSpec,
                passwordEncoder,
                accountRepository,
                roleRepository
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
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        //WHEN
        Optional<Account> respondeAccount = accountService.findById(1);
        //THEN
        Assertions.assertTrue(respondeAccount.isPresent());
        Assertions.assertEquals(account, respondeAccount.get());
        verify(accountRepository).findById(1L);
    }

    @Test
    void saveNewAccountAndNotHasHole() {
        //GIVEN
        Account account = Account.builder().password("password").build();
        Role roleUser = Role.builder().id(1L).build();
        when(accountRepository.save(account)).thenReturn(account);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(passwordEncoder.encode("password")).thenReturn("drowssap");
        //WHEN
        Account accountResponse = accountService.save(account);
        //THEN
        Assertions.assertEquals(account, accountResponse);
        Assertions.assertEquals(Collections.singleton(roleUser), accountResponse.getRoles());
        Assertions.assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleRepository).findByName("ROLE_USER");
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
        Assertions.assertEquals(account, accountResponse);
        Assertions.assertEquals(Collections.singleton(roleUser), accountResponse.getRoles());
        Assertions.assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleRepository, never()).findByName("ROLE_USER");
    }

    @Test
    void saveUpdateAccountAndNotHasHole() {
        //GIVEN
        Account account = Account.builder().id(1L).password("password").build();
        Role roleUser = Role.builder().id(1L).build();
        when(accountRepository.save(account)).thenReturn(account);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(passwordEncoder.encode("password")).thenReturn("drowssap");
        //WHEN
        Account accountResponse = accountService.save(account);
        //THEN
        Assertions.assertEquals(account, accountResponse);
        Assertions.assertTrue(accountResponse.getRoles().isEmpty());
        Assertions.assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleRepository, never()).findByName("ROLE_USER");
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
        Assertions.assertEquals(account, accountResponse);
        Assertions.assertEquals(Collections.singleton(roleUser), accountResponse.getRoles());
        Assertions.assertEquals("drowssap", accountResponse.getPassword());
        verify(accountRepository).save(account);
        verify(roleRepository, never()).findByName("ROLE_USER");
    }

    @Test
    void findOneByEmail() {
        //GIVEN
        Account account = Account.builder().id(1L).build();
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com")).thenReturn(Optional.of(account));
        //WHEN
        Optional<Account> accountResponse = accountService.findOneByEmail("user@domain.com");
        //THEN
        Assertions.assertTrue(accountResponse.isPresent());
        Assertions.assertEquals(account, accountResponse.get());
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
    }

    @Test
    void findOneByUsername() {
        //GIVEN
        Account account = Account.builder().id(1L).build();
        when(accountRepository.findOneByUsernameIgnoreCase("username")).thenReturn(Optional.of(account));
        //WHEN
        Optional<Account> accountResponse = accountService.findOneByUsername("username");
        //THEN
        Assertions.assertTrue(accountResponse.isPresent());
        Assertions.assertEquals(account, accountResponse.get());
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void attemptUserRegistration() {
        //GIVEN
        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .usernameExists(false)
                .emailExists(false)
                .build();
        Account account = Account.builder().username("username").email("user@domain.com").build();
        when(accountRepository.findOneByUsernameIgnoreCase("username")).thenReturn(Optional.empty());
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com")).thenReturn(Optional.empty());
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.attemptUserRegistration(account);
        //THEN
        Assertions.assertEquals(registrationResponse, registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void attemptUserRegistrationUsernameExists() {
        //GIVEN
        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .usernameExists(false)
                .emailExists(true)
                .build();
        Account account = Account.builder().username("username").email("user@domain.com").build();
        when(accountRepository.findOneByUsernameIgnoreCase("username")).thenReturn(Optional.of(account));
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com")).thenReturn(Optional.empty());
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.attemptUserRegistration(account);
        //THEN
        Assertions.assertEquals(registrationResponse, registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void attemptUserRegistrationEmailExists() {
        //GIVEN
        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .usernameExists(true)
                .emailExists(false)
                .build();
        Account account = Account.builder().username("username").email("user@domain.com").build();
        when(accountRepository.findOneByUsernameIgnoreCase("username")).thenReturn(Optional.empty());
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com")).thenReturn(Optional.of(account));
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.attemptUserRegistration(account);
        //THEN
        Assertions.assertEquals(registrationResponse, registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    @Test
    void attemptUserRegistrationUsernameAndEmailExists() {
        //GIVEN
        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .usernameExists(true)
                .emailExists(true)
                .build();
        Account account = Account.builder().username("username").email("user@domain.com").build();
        when(accountRepository.findOneByUsernameIgnoreCase("username")).thenReturn(Optional.of(account));
        when(accountRepository.findOneByEmailIgnoreCase("user@domain.com")).thenReturn(Optional.of(account));
        //WHEN
        RegistrationResponse registrationResponseResponse = accountService.attemptUserRegistration(account);
        //THEN
        Assertions.assertEquals(registrationResponse, registrationResponseResponse);
        verify(accountRepository).findOneByEmailIgnoreCase("user@domain.com");
        verify(accountRepository).findOneByUsernameIgnoreCase("username");
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}