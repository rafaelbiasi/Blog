package br.com.rafaelbiasi.blog.core.service;

import br.com.rafaelbiasi.blog.core.model.User;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.core.vo.RegistrationResponse;

import java.util.Optional;

public interface UserService extends EntityService<User> {

	SimplePage<User> findAll(SimplePageRequest pageable);

	Optional<User> findOneByEmail(final String email);

	Optional<User> findOneByUsername(final String username);

	void registerUser(final User user);

	RegistrationResponse checkEmailAndUsernameExists(final User user);
}
