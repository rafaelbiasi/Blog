package br.com.rafaelbiasi.blog.core.service;

import br.com.rafaelbiasi.blog.core.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService extends EntityService<Role> {

	Optional<Role> findByName(final String name);

	List<Role> findAll();
}
