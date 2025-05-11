package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.Role;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

	Optional<Role> findByName(final String name);

	Optional<Role> findById(long id);

	void delete(Role role);

	Role save(Role role);

	List<Role> findAll();
}
