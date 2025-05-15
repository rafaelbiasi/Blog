package br.com.rafaelbiasi.blog.core.domain.service;

import br.com.rafaelbiasi.blog.core.domain.model.RoleModel;

import java.util.List;
import java.util.Optional;

public interface RoleService extends EntityService<RoleModel> {

	Optional<RoleModel> findByName(final String name);

	List<RoleModel> findAll();
}
