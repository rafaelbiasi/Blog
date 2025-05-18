package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.model.User;
import br.com.rafaelbiasi.blog.core.repository.UserRepository;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.UserEntity;
import br.com.rafaelbiasi.blog.infrastructure.persistence.mapper.UserEntityMapper;
import br.com.rafaelbiasi.blog.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository repository;
	private final UserEntityMapper entityMapper;

	@Override
	public Optional<User> findOneByEmailIgnoreCase(String email) {
		return repository.findOneByEmailIgnoreCase(email).map(entityMapper::toModel);
	}

	@Override
	public Optional<User> findOneByUsernameIgnoreCase(String username) {
		return repository.findOneByUsernameIgnoreCase(username).map(entityMapper::toModel);
	}

	@Override
	public Optional<User> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(User user) {
		UserEntity userEntity = entityMapper.toEntity(user);
		repository.delete(userEntity);
	}

	@Override
	public User save(User user) {
		UserEntity userEntity = entityMapper.toEntity(user);
		UserEntity savedUserEntity = repository.save(userEntity);
		return entityMapper.toModel(savedUserEntity);
	}

	@Override
	public SimplePage<User> findAll(SimplePageRequest pageable) {
		Pageable pageRequest = PageRequest.of(pageable.pageNumber(), pageable.pageSize());
		Page<User> page = repository.findAll(pageRequest)
				.map(entityMapper::toModel);
		return SimplePage.of(page.getContent(), pageable, page.getTotalElements());
	}
}
