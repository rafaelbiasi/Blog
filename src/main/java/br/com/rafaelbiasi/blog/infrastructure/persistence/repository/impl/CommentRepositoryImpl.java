package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.domain.model.CommentModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.repository.CommentRepository;
import br.com.rafaelbiasi.blog.infrastructure.persistence.mapper.CommentEntityMapper;
import br.com.rafaelbiasi.blog.infrastructure.persistence.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

	private final CommentJpaRepository repository;
	private final CommentEntityMapper entityMapper;

	@Override
	public Optional<CommentModel> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(CommentModel comment) {
		repository.delete(entityMapper.toEntity(comment));
	}

	@Override
	public CommentModel save(CommentModel comment) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(comment)));
	}

	@Override
	public PageModel<CommentModel> findAll(PageRequestModel pageable) {
		val page = repository.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()))
				.map(entityMapper::toModel);
		return PageModel.of(page.getContent(), pageable, page.getTotalElements());
	}
}
