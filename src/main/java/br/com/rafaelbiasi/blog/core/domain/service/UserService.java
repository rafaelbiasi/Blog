package br.com.rafaelbiasi.blog.core.domain.service;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponseModel;
import br.com.rafaelbiasi.blog.core.domain.model.UserModel;

import java.util.Optional;

public interface UserService extends EntityService<UserModel> {

	PageModel<UserModel> findAll(PageRequestModel pageable);

	Optional<UserModel> findOneByEmail(final String email);

	Optional<UserModel> findOneByUsername(final String username);

	void registerUser(final UserModel user);

	RegistrationResponseModel checkEmailAndUsernameExists(final UserModel user);
}
