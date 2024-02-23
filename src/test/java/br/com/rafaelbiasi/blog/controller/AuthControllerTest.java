package br.com.rafaelbiasi.blog.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    private AuthController authController;
    private AutoCloseable closeable;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        authController = new AuthController();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void auth() {
        //WHEN
        String view = authController.auth();
        //THEN
        Assertions.assertEquals("auth", view);
    }

    @Test()
    void error() {
        //GIVEN
        when(model.addAttribute("errorMessage", "A sua senha ou o seu login estão errados")).thenReturn(model);
        //WHEN
        String view = authController.error(model);
        //THEN
        Assertions.assertEquals("auth", view);
        verify(model).addAttribute("errorMessage", "A sua senha ou o seu login estão errados");
    }

    //    @Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}