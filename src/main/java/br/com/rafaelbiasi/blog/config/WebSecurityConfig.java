package br.com.rafaelbiasi.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    public static final String[] PERMIT_ALL = {
            "/css/**",
            "/js/**",
            "/images/**",
            "/fonts/**",
            "/webjars/**",
            "/",
            "/page/**",
            "/rss/**",
            "/register/**",
            "/posts/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(WebSecurityConfig::authorizeHttpRequests)
                .formLogin(WebSecurityConfig::formAuth)
                .logout(LogoutConfigurer::permitAll)
                .build();
    }

    private static void authorizeHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth.requestMatchers(PERMIT_ALL)
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    private static void formAuth(FormLoginConfigurer<HttpSecurity> form) {
        form.loginPage("/auth")
                .loginProcessingUrl("/auth")
                .usernameParameter("user")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/auth?error")
                .permitAll();
    }
}
