package br.com.rafaelbiasi.blog.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AppConfigTest {

    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        //GIVEN
        appConfig = new AppConfig();
    }

    @Test
    void passwordEncoder() {
        //WHEN
        PasswordEncoder passwordEncoder = appConfig.passwordEncoder();
        //THEN
        Assertions.assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);
    }
}