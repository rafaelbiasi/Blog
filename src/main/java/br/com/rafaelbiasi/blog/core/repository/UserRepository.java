package br.com.rafaelbiasi.blog.core.repository;

import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.core.model.User;

import java.util.Optional;

public interface UserRepository {

	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findOneByUsernameIgnoreCase(String username);

	Optional<User> findById(long id);

	void delete(User user);

	User save(User user);

	SimplePage<User> findAll(SimplePageRequest pageable);
}
