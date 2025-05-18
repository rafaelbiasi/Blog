package br.com.rafaelbiasi.blog.infrastructure.service;

import br.com.rafaelbiasi.blog.core.model.User;
import br.com.rafaelbiasi.blog.core.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(final String usernameOrEmail) throws UsernameNotFoundException {
		requireNonNull(usernameOrEmail, "The Username or E-mail has a null value.");
		return findOneByUsername(usernameOrEmail)
				.or(() -> findOneByEmail(usernameOrEmail))
				.map(this::user)
				.orElseThrow(() -> new UsernameNotFoundException("User not found or password is not correct"));
	}

	private static org.springframework.security.core.userdetails.User user(
			final User user,
			final List<SimpleGrantedAuthority> grantedAuthorities
	) {
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				grantedAuthorities
		);
	}

	private Optional<User> findOneByUsername(String usernameOrEmail) {
		return userService.findOneByUsername(usernameOrEmail);
	}

	private Optional<User> findOneByEmail(String usernameOrEmail) {
		return userService.findOneByEmail(usernameOrEmail);
	}

	private org.springframework.security.core.userdetails.User user(final User user) {
		return user
				.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(collectingAndThen(
						toList(),
						grantedAuthorities -> user(user, grantedAuthorities)
				));
	}
}
