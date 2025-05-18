package br.com.rafaelbiasi.blog.core.service.impl;

import br.com.rafaelbiasi.blog.core.model.Role;
import br.com.rafaelbiasi.blog.core.repository.RoleRepository;
import br.com.rafaelbiasi.blog.core.service.RoleService;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Optional<Role> findById(final long id) {
		return roleRepository.findById(id);
	}

	@Override
	public void delete(final Role role) {
		roleRepository.delete(role);
	}

	@Override
	public Role save(final Role role) {
		requireNonNull(role, "The Role has a null value.");
		return roleRepository.save(role);
	}

	@Override
	public Optional<Role> findByName(final String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
}
