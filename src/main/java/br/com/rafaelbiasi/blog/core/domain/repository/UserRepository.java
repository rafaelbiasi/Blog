package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public interface UserRepository {

	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findOneByUsernameIgnoreCase(String username);

	Optional<User> findById(long id);

	void delete(User user);

	User save(User user);

	Page<User> findAll(Pageable pageable);
}
