package br.com.rafaelbiasi.blog.core.service;

import br.com.rafaelbiasi.blog.core.model.Comment;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.util.Optional;

public interface CommentService extends EntityService<Comment> {

	Optional<Comment> findByCode(final String code);

	Comment save(final Comment comment);

	SimplePage<Comment> findAll(SimplePageRequest pageable);
}
