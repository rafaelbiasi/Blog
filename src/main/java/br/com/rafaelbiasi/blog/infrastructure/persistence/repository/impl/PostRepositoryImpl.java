package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.PostModel;
import br.com.rafaelbiasi.blog.core.domain.repository.PostRepository;
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
	public Optional<PostModel> findById(long id) {
		return repository.findById(id).map(entityMapper::toModel);
	}

	@Override
	public void delete(PostModel post) {
		repository.delete(entityMapper.toEntity(post));
	}

	@Override
	public PostModel save(PostModel post) {
		return entityMapper.toModel(repository.save(entityMapper.toEntity(post)));
	}

	@Override
	public List<PostModel> findAll() {
		return repository.findAll().stream().map(entityMapper::toModelWithoutComments).toList();
	}

	@Override
	public PageModel<PostModel> findAll(PageRequestModel pageable) {
		Page<PostModel> page = repository.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()))
				.map(entityMapper::toModelWithoutComments);
		return PageModel.of(page.getContent(), pageable, page.getTotalElements());
	}
}
