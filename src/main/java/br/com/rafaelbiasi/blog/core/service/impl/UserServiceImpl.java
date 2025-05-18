package br.com.rafaelbiasi.blog.core.service.impl;

import br.com.rafaelbiasi.blog.core.model.User;
import br.com.rafaelbiasi.blog.core.repository.UserRepository;
import br.com.rafaelbiasi.blog.core.service.PasswordEncoderService;
import br.com.rafaelbiasi.blog.core.service.RoleService;
import br.com.rafaelbiasi.blog.core.service.UserService;
import br.com.rafaelbiasi.blog.core.vo.RegistrationResponse;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;

public class UserServiceImpl implements UserService {

	private final PasswordEncoderService passwordEncoderService;
	private final UserRepository userRepository;
	private final RoleService roleService;

	public UserServiceImpl(final PasswordEncoderService passwordEncoderService,
						   final UserRepository userRepository,
						   final RoleService roleService) {
		this.passwordEncoderService = passwordEncoderService;
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	@Override
	public Optional<User> findById(final long id) {
		return userRepository.findById(id);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(final User user) {
		userRepository.delete(user);
	}

	@Override
	public User save(final User user) {
		requireNonNull(user, "The User has a null value.");
		of(user).filter(User::isNew)
				.filter(User::hasNoHoles)
				.ifPresent(acc -> roleService
						.findByName("ROLE_USER")
						.map(Collections::singleton)
						.ifPresent(acc::setRoles)
				);
		user.setPassword(passwordEncoderService.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public SimplePage<User> findAll(SimplePageRequest pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> findOneByEmail(final String email) {
		requireNonNull(email, "The E-mail has a null value.");
		return userRepository.findOneByEmailIgnoreCase(email);
	}

	@Override
	public Optional<User> findOneByUsername(final String username) {
		requireNonNull(username, "The Username has a null value.");
		return userRepository.findOneByUsernameIgnoreCase(username);
	}

	@Override
	public void registerUser(final User user) {
		requireNonNull(user, "The User has a null value.");
		save(user);
	}

	@Override
	public RegistrationResponse checkEmailAndUsernameExists(final User user) {
		return RegistrationResponse.builder()
				.emailExists(isEmailExists(user))
				.usernameExists(isUsernameExists(user))
				.build();
	}

	private boolean isUsernameExists(final User user) {
		return userRepository.findOneByUsernameIgnoreCase(user.getUsername()).isPresent();
	}

	private boolean isEmailExists(final User user) {
		return userRepository.findOneByEmailIgnoreCase(user.getEmail()).isPresent();
	}
}
