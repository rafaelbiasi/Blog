package br.com.rafaelbiasi.blog.core.repository;

import br.com.rafaelbiasi.blog.core.model.Comment;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.util.Optional;

public interface CommentRepository {

	Optional<Comment> findById(long id);

	void delete(Comment comment);

	Comment save(Comment comment);

	SimplePage<Comment> findAll(SimplePageRequest pageable);

}
