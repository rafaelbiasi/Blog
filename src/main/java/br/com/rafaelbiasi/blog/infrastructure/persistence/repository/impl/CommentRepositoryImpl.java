package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.model.Comment;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.core.repository.CommentRepository;
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
	public Optional<Comment> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(Comment comment) {
		repository.delete(entityMapper.toEntity(comment));
	}

	@Override
	public Comment save(Comment comment) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(comment)));
	}

	@Override
	public SimplePage<Comment> findAll(SimplePageRequest pageable) {
		val page = repository.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()))
				.map(entityMapper::toModel);
		return SimplePage.of(page.getContent(), pageable, page.getTotalElements());
	}
}
