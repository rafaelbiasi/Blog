package br.com.rafaelbiasi.blog.domain.repository;

import br.com.rafaelbiasi.blog.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository {

	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findOneByUsernameIgnoreCase(String username);

	Optional<User> findById(long id);

	void delete(User user);

	User save(User user);

	Page<User> findAll(Pageable pageable);
}
