package br.com.rafaelbiasi.blog.domain.repository;

import br.com.rafaelbiasi.blog.domain.model.Role;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RoleRepository {

	Optional<Role> findByName(final String name);

	Optional<Role> findById(long id);

	void delete(Role role);

	Role save(Role role);

	List<Role> findAll();
}
