package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.RoleModel;
import br.com.rafaelbiasi.blog.core.domain.repository.RoleRepository;
import br.com.rafaelbiasi.blog.core.domain.service.RoleService;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Optional<RoleModel> findById(final long id) {
		return roleRepository.findById(id);
	}

	@Override
	public void delete(final RoleModel role) {
		roleRepository.delete(role);
	}

	@Override
	public RoleModel save(final RoleModel role) {
		requireNonNull(role, "The Role has a null value.");
		return roleRepository.save(role);
	}

	@Override
	public Optional<RoleModel> findByName(final String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public List<RoleModel> findAll() {
		return roleRepository.findAll();
	}
}
