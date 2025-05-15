package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.domain.model.RoleModel;
import br.com.rafaelbiasi.blog.core.domain.repository.RoleRepository;
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
	public Optional<RoleModel> findByName(String name) {
		return repository.findByName(name).map(entityMapper::toModel);
	}

	@Override
	public Optional<RoleModel> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(RoleModel role) {
		repository.delete(entityMapper.toEntity(role));
	}

	@Override
	public RoleModel save(RoleModel role) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(role)));
	}

	@Override
	public List<RoleModel> findAll() {
		return repository.findAll().stream().map(entityMapper::toModel).toList();
	}
}
