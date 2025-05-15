package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.UserModel;

import java.util.Optional;

public interface UserRepository {

	Optional<UserModel> findOneByEmailIgnoreCase(String email);

	Optional<UserModel> findOneByUsernameIgnoreCase(String username);

	Optional<UserModel> findById(long id);

	void delete(UserModel user);

	UserModel save(UserModel user);

	PageModel<UserModel> findAll(PageRequestModel pageable);
}
