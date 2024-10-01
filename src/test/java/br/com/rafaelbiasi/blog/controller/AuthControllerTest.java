package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.ui.controller.AuthController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    private AuthController authController;
    @Mock
    private Model model;
    private AutoCloseable closeable;

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
        assertEquals("user/auth", view);
    }

    @Test()
    void error() {
        //GIVEN
        when(model.addAttribute("authError", true)).thenReturn(model);
        //WHEN
        String view = authController.error(model);
        //THEN
        assertEquals("user/auth", view);
        verify(model).addAttribute("authError", true);
    }

    //    @Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}