package br.com.rafaelbiasi.blog.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthControllerTest {

    private AuthController authController;

    @BeforeEach
    void setUp() {
        //GIVEN
        authController = new AuthController();
    }

    @Test
    void auth() {
        //WHEN
        String view = authController.auth();
        //THEN
        Assertions.assertEquals("auth", view);
    }
}