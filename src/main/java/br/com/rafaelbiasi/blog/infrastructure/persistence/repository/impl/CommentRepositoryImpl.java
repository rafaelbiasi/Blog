package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.domain.model.Comment;
import br.com.rafaelbiasi.blog.core.domain.repository.CommentRepository;
import br.com.rafaelbiasi.blog.infrastructure.persistence.mapper.CommentEntityMapper;
import br.com.rafaelbiasi.blog.infrastructure.persistence.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<Comment> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(entityMapper::toModel);
	}
}
