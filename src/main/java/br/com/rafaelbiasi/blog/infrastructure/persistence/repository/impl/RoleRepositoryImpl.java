package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.model.Role;
import br.com.rafaelbiasi.blog.core.repository.RoleRepository;
import br.com.rafaelbiasi.blog.infrastructure.persistence.mapper.RoleEntityMapper;
import br.com.rafaelbiasi.blog.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

	private final RoleJpaRepository repository;
	private final RoleEntityMapper entityMapper;

	@Override
	public Optional<Role> findByName(String name) {
		return repository.findByName(name).map(entityMapper::toModel);
	}

	@Override
	public Optional<Role> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(Role role) {
		repository.delete(entityMapper.toEntity(role));
	}

	@Override
	public Role save(Role role) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(role)));
	}

	@Override
	public List<Role> findAll() {
		return repository.findAll().stream().map(entityMapper::toModel).toList();
	}
}
