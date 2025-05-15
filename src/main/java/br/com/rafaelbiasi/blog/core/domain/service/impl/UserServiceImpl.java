package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponseModel;
import br.com.rafaelbiasi.blog.core.domain.model.UserModel;
import br.com.rafaelbiasi.blog.core.domain.repository.UserRepository;
import br.com.rafaelbiasi.blog.core.domain.service.PasswordEncoderService;
import br.com.rafaelbiasi.blog.core.domain.service.RoleService;
import br.com.rafaelbiasi.blog.core.domain.service.UserService;

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
	public Optional<UserModel> findById(final long id) {
		return userRepository.findById(id);
	}

	@Override
	public void delete(final UserModel user) {
		userRepository.delete(user);
	}

	@Override
	public UserModel save(final UserModel user) {
		requireNonNull(user, "The User has a null value.");
		of(user).filter(UserModel::isNew)
				.filter(UserModel::hasNoHoles)
				.ifPresent(acc -> roleService
						.findByName("ROLE_USER")
						.map(Collections::singleton)
						.ifPresent(acc::setRoles)
				);
		user.setPassword(passwordEncoderService.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public PageModel<UserModel> findAll(PageRequestModel pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<UserModel> findOneByEmail(final String email) {
		requireNonNull(email, "The E-mail has a null value.");
		return userRepository.findOneByEmailIgnoreCase(email);
	}

	@Override
	public Optional<UserModel> findOneByUsername(final String username) {
		requireNonNull(username, "The Username has a null value.");
		return userRepository.findOneByUsernameIgnoreCase(username);
	}

	@Override
	public void registerUser(final UserModel user) {
		requireNonNull(user, "The User has a null value.");
		save(user);
	}

	@Override
	public RegistrationResponseModel checkEmailAndUsernameExists(final UserModel user) {
		return RegistrationResponseModel.builder()
				.emailExists(isEmailExists(user))
				.usernameExists(isUsernameExists(user))
				.build();
	}

	private boolean isUsernameExists(final UserModel user) {
		return userRepository.findOneByUsernameIgnoreCase(user.getUsername()).isPresent();
	}

	private boolean isEmailExists(final UserModel user) {
		return userRepository.findOneByEmailIgnoreCase(user.getEmail()).isPresent();
	}
}
