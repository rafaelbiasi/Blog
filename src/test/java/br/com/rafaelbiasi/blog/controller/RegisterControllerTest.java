package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.RegistrationResponseData;
import br.com.rafaelbiasi.blog.facade.AccountFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.when;

class RegisterControllerTest {

    private RegisterController registerController;
    private AutoCloseable closeable;
    @Mock
    private AccountFacade accountFacade;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        registerController = new RegisterController(accountFacade);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void register() {
        //GIVEN
        //WHEN
        String view = registerController.register(model);
        //THEN
        Assertions.assertEquals("register", view);
        Mockito.verify(model).addAttribute("account", new AccountData());
    }

    @Test
    void registerSuccess() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(false)
                .usernameExists(false)
                .build();
        when(accountFacade.attemptUserRegistration(accountData)).thenReturn(registrationResponse);
        //WHEN
        String view = registerController.register(accountData, model);
        //THEN
        Assertions.assertEquals("redirect:/", view);
        Mockito.verify(accountFacade).attemptUserRegistration(accountData);
    }

    @Test
    void registerEmailExist() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(true)
                .usernameExists(false)
                .build();
        when(accountFacade.attemptUserRegistration(accountData)).thenReturn(registrationResponse);
        //WHEN
        String view = registerController.register(accountData, model);
        //THEN
        Assertions.assertEquals("register", view);
        Mockito.verify(accountFacade).attemptUserRegistration(accountData);
        Mockito.verify(model).addAttribute("usernameExists", registrationResponse.usernameExists());
        Mockito.verify(model).addAttribute("emailExists", registrationResponse.emailExists());
        Mockito.verify(model).addAttribute("account", new AccountData());
    }

    @Test
    void registerUsernameExist() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(false)
                .usernameExists(true)
                .build();
        when(accountFacade.attemptUserRegistration(accountData)).thenReturn(registrationResponse);
        //WHEN
        String view = registerController.register(accountData, model);
        //THEN
        Assertions.assertEquals("register", view);
        Mockito.verify(accountFacade).attemptUserRegistration(accountData);
        Mockito.verify(model).addAttribute("usernameExists", registrationResponse.usernameExists());
        Mockito.verify(model).addAttribute("emailExists", registrationResponse.emailExists());
        Mockito.verify(model).addAttribute("account", new AccountData());
    }

    @Test
    void registerUsernameAndEmailExist() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(true)
                .usernameExists(true)
                .build();
        when(accountFacade.attemptUserRegistration(accountData)).thenReturn(registrationResponse);
        //WHEN
        String view = registerController.register(accountData, model);
        //THEN
        Assertions.assertEquals("register", view);
        Mockito.verify(accountFacade).attemptUserRegistration(accountData);
        Mockito.verify(model).addAttribute("usernameExists", registrationResponse.usernameExists());
        Mockito.verify(model).addAttribute("emailExists", registrationResponse.emailExists());
        Mockito.verify(model).addAttribute("account", new AccountData());
    }

    @Test
    void registerException() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        when(accountFacade.attemptUserRegistration(accountData)).thenThrow(RuntimeException.class);
        //WHEN
        String view = registerController.register(accountData, model);
        //THEN
        Assertions.assertEquals("error403", view);
        Mockito.verify(accountFacade).attemptUserRegistration(accountData);
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}