package br.com.rafaelbiasi.blog.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Value("${security.encoding-strength}")
	final int strength = 12;

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(this::authorizeHttpRequests)
				.formLogin(this::formAuth)
				.logout(LogoutConfigurer::permitAll)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(strength);
	}

	private void authorizeHttpRequests(
			final AuthorizeHttpRequestsConfigurer<HttpSecurity>
					.AuthorizationManagerRequestMatcherRegistry auth
	) {
		auth.requestMatchers("/admin/**").authenticated()
				.anyRequest().permitAll();
	}

	private void formAuth(final FormLoginConfigurer<HttpSecurity> form) {
		form.loginPage("/auth/")
				.loginProcessingUrl("/auth/")
				.usernameParameter("user")
				.passwordParameter("password")
				.defaultSuccessUrl("/")
				.failureForwardUrl("/auth/error/")
				.permitAll();
	}
}
