package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.RegistrationResponseData;
import br.com.rafaelbiasi.blog.model.Account;
import br.com.rafaelbiasi.blog.model.RegistrationResponse;
import br.com.rafaelbiasi.blog.service.AccountService;
import br.com.rafaelbiasi.blog.transformer.Transformer;
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
    private Transformer<Account, AccountData> accountDataTransformer;
    @Mock
    private Transformer<AccountData, Account> accountTransformer;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        accountFacade = new AccountFacadeImpl(accountService, accountDataTransformer, accountTransformer);
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
        when(accountDataTransformer.convert(account)).thenReturn(accountData);
        //WHEN
        Optional<AccountData> accountResponse = accountFacade.findOneByEmail("emai@email.com");
        //THEN
        assertTrue(accountResponse.isPresent());
        assertEquals(accountData, accountResponse.get());
        verify(accountService).findOneByEmail("emai@email.com");
        verify(accountDataTransformer).convert(account);
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
        when(accountDataTransformer.convert(account)).thenReturn(accountData);
        //WHEN
        Optional<AccountData> accountResponse = accountFacade.findOneByUsername("username");
        //THEN
        assertTrue(accountResponse.isPresent());
        assertEquals(accountData, accountResponse.get());
        verify(accountService).findOneByUsername("username");
        verify(accountDataTransformer).convert(account);
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
        when(accountTransformer.convert(accountData)).thenReturn(account);
        when(accountService.save(account)).thenReturn(account);
        //WHEN
        accountFacade.save(accountData);
        //THEN
        verify(accountTransformer).convert(accountData);
        verify(accountService).save(account);
    }

    @Test()
    void attemptUserRegistration() {
        //GIVENs
        AccountData accountData = new AccountData();
        Account account = new Account();
        RegistrationResponseData resultData = new RegistrationResponseData(true, true);
        RegistrationResponse result = new RegistrationResponse(true, true);
        when(accountTransformer.convert(accountData)).thenReturn(account);
        when(accountService.attemptUserRegistration(account)).thenReturn(result);
        //WHEN
        RegistrationResponseData registrationResponse = accountFacade.attemptUserRegistration(accountData);
        //THEN
        assertEquals(resultData, registrationResponse);
        verify(accountTransformer).convert(accountData);
        verify(accountService).attemptUserRegistration(account);
    }

    @Test()
    void checkEmailAndUsernameExists() {
        //GIVENs
        AccountData accountData = new AccountData();
        Account account = new Account();
        RegistrationResponseData resultData = new RegistrationResponseData(true, true);
        RegistrationResponse result = new RegistrationResponse(true, true);
        when(accountTransformer.convert(accountData)).thenReturn(account);
        when(accountService.checkEmailAndUsernameExists(account)).thenReturn(result);
        //WHEN
        RegistrationResponseData registrationResponse = accountFacade.checkEmailAndUsernameExists(accountData);
        //THEN
        assertEquals(resultData, registrationResponse);
        verify(accountTransformer).convert(accountData);
        verify(accountService).checkEmailAndUsernameExists(account);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}