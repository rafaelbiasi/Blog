package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.model.Post;
import br.com.rafaelbiasi.blog.core.repository.PostRepository;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.infrastructure.persistence.mapper.PostEntityMapper;
import br.com.rafaelbiasi.blog.infrastructure.persistence.repository.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

	private final PostJpaRepository repository;
	private final PostEntityMapper entityMapper;

	@Override
	public Optional<Post> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(Post post) {
		repository.delete(entityMapper.toEntity(post));
	}

	@Override
	public Post save(Post post) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(post)));
	}

	@Override
	public List<Post> findAll() {
		return repository.findAll().stream().map(entityMapper::toModelWithoutComments).toList();
	}

	@Override
	public SimplePage<Post> findAll(SimplePageRequest pageable) {
		Page<Post> page = repository.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()))
				.map(entityMapper::toModelWithoutComments);
		return SimplePage.of(page.getContent(), pageable, page.getTotalElements());
	}
}
