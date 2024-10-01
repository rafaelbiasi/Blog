package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.application.facade.AccountFacade;
import br.com.rafaelbiasi.blog.application.facade.impl.AccountFacadeImpl;
import br.com.rafaelbiasi.blog.application.mapper.AccountMapper;
import br.com.rafaelbiasi.blog.domain.entity.Account;
import br.com.rafaelbiasi.blog.domain.microtype.RegistrationResponse;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountFacadeImplTest {

    private AccountFacade accountFacade;
    @Mock
    private AccountService accountService;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        accountFacade = new AccountFacadeImpl(accountService, accountMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findOneByEmail() {
        //GIVEN
        Account account = new Account();
        AccountData accountData = new AccountData();
        when(accountService.findOneByEmail("emai@email.com")).thenReturn(of(account));
        //WHEN
        Optional<AccountData> accountResponse = accountFacade.findOneByEmail("emai@email.com");
        //THEN
        assertTrue(accountResponse.isPresent());
        assertEquals(accountData, accountResponse.get());
        verify(accountService).findOneByEmail("emai@email.com");
    }

    @Test
    void findOneByEmailEmpty() {
        //GIVEN
        when(accountService.findOneByEmail("emai@email.com")).thenReturn(empty());
        //WHEN
        Optional<AccountData> accountResponse = accountFacade.findOneByEmail("emai@email.com");
        //THEN
        assertTrue(accountResponse.isEmpty());
        verify(accountService).findOneByEmail("emai@email.com");
    }

    @Test
    void findOneByUsername() {
        //GIVEN
        Account account = new Account();
        AccountData accountData = new AccountData();
        when(accountService.findOneByUsername("username")).thenReturn(of(account));
        //WHEN
        Optional<AccountData> accountResponse = accountFacade.findOneByUsername("username");
        //THEN
        assertTrue(accountResponse.isPresent());
        assertEquals(accountData, accountResponse.get());
        verify(accountService).findOneByUsername("username");
    }

    @Test
    void findOneByUsernameEmpty() {
        //GIVEN
        when(accountService.findOneByUsername("username")).thenReturn(empty());
        //WHEN
        Optional<AccountData> accountResponse = accountFacade.findOneByUsername("username");
        //THEN
        assertTrue(accountResponse.isEmpty());
        verify(accountService).findOneByUsername("username");
    }


    @Test()
    void save() {
        //GIVEN
        AccountData accountData = new AccountData();
        Account account = new Account();
        when(accountService.save(account)).thenReturn(account);
        //WHEN
        accountFacade.save(accountData);
        //THEN
        verify(accountService).save(account);
    }

    @Test()
    void registerUser() {
        //GIVENs
        AccountData accountData = new AccountData();
        Account account = new Account();
        //WHEN
        accountFacade.registerUser(accountData);
        //THEN
        verify(accountService).registerUser(account);
    }

    @Test()
    void checkEmailAndUsernameExists() {
        //GIVENs
        AccountData accountData = new AccountData();
        Account account = new Account();
        RegistrationResponse resultData = new RegistrationResponse(true, true);
        RegistrationResponse result = new RegistrationResponse(true, true);
        when(accountService.checkEmailAndUsernameExists(account)).thenReturn(result);
        //WHEN
        RegistrationResponse registrationResponse = accountFacade.checkEmailAndUsernameExists(accountData);
        //THEN
        assertEquals(resultData, registrationResponse);
        verify(accountService).checkEmailAndUsernameExists(account);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}