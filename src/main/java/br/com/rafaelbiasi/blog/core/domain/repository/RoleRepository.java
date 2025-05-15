package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.RoleModel;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

	Optional<RoleModel> findByName(final String name);

	Optional<RoleModel> findById(long id);

	void delete(RoleModel role);

	RoleModel save(RoleModel role);

	List<RoleModel> findAll();
}
