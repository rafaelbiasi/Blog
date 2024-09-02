package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.RegistrationResponseData;
import br.com.rafaelbiasi.blog.facade.AccountFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterControllerTest {

    private RegisterController registerController;
    @Mock
    private AccountFacade accountFacade;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    private AutoCloseable closeable;

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
        assertEquals("user/register", view);
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
        when(accountFacade.checkEmailAndUsernameExists(accountData)).thenReturn(registrationResponse);
        when(accountFacade.attemptUserRegistration(accountData)).thenReturn(registrationResponse);
        //WHEN
        String view = registerController.register(accountData, bindingResult, model);
        //THEN
        assertEquals("redirect:/", view);
        verify(accountFacade).checkEmailAndUsernameExists(accountData);
        verify(accountFacade).attemptUserRegistration(accountData);
    }

    @Test
    void registerUsernameExist() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(false)
                .usernameExists(true)
                .build();
        when(accountFacade.checkEmailAndUsernameExists(accountData)).thenReturn(registrationResponse);
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String view = registerController.register(accountData, bindingResult, model);
        //THEN
        assertEquals("user/register", view);
        verify(accountFacade).checkEmailAndUsernameExists(accountData);
        verify(bindingResult).hasErrors();
        verify(model).addAttribute("account", accountData);
    }

    @Test
    void registerEmailExist() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(true)
                .usernameExists(false)
                .build();
        when(accountFacade.checkEmailAndUsernameExists(accountData)).thenReturn(registrationResponse);
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String view = registerController.register(accountData, bindingResult, model);
        //THEN
        assertEquals("user/register", view);
        verify(accountFacade).checkEmailAndUsernameExists(accountData);
        verify(bindingResult).hasErrors();
        verify(model).addAttribute("account", accountData);
    }

    @Test
    void registerUsernameAndEmailExist() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(true)
                .usernameExists(true)
                .build();
        when(accountFacade.checkEmailAndUsernameExists(accountData)).thenReturn(registrationResponse);
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        String view = registerController.register(accountData, bindingResult, model);
        //THEN
        assertEquals("user/register", view);
        verify(accountFacade).checkEmailAndUsernameExists(accountData);
        verify(bindingResult).hasErrors();
        verify(model).addAttribute("account", accountData);
    }

    @Test
    void registerException() {
        //GIVEN
        AccountData accountData = AccountData.builder().build();
        RegistrationResponseData registrationResponse = RegistrationResponseData.builder()
                .emailExists(false)
                .usernameExists(false)
                .build();
        when(accountFacade.checkEmailAndUsernameExists(accountData)).thenReturn(registrationResponse);
        when(accountFacade.attemptUserRegistration(accountData)).thenThrow(RuntimeException.class);
        //WHEN
        Executable executable = () -> registerController.register(accountData, bindingResult, model);
        //THEN
        assertThrows(RuntimeException.class, executable);
        verify(accountFacade).checkEmailAndUsernameExists(accountData);
        verify(accountFacade).attemptUserRegistration(accountData);
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}