package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.UserModel;
import br.com.rafaelbiasi.blog.core.domain.repository.UserRepository;
import br.com.rafaelbiasi.blog.infrastructure.persistence.mapper.UserEntityMapper;
import br.com.rafaelbiasi.blog.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository repository;
	private final UserEntityMapper entityMapper;

	@Override
	public Optional<UserModel> findOneByEmailIgnoreCase(String email) {
		return repository.findOneByEmailIgnoreCase(email).map(entityMapper::toModel);
	}

	@Override
	public Optional<UserModel> findOneByUsernameIgnoreCase(String username) {
		return repository.findOneByUsernameIgnoreCase(username).map(entityMapper::toModel);
	}

	@Override
	public Optional<UserModel> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(UserModel user) {
		repository.delete(entityMapper.toEntity(user));
	}

	@Override
	public UserModel save(UserModel user) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(user)));
	}

	@Override
	public PageModel<UserModel> findAll(PageRequestModel pageable) {
		Page<UserModel> page = repository.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()))
				.map(entityMapper::toModel);
		return PageModel.of(page.getContent(), pageable, page.getTotalElements());
	}
}
