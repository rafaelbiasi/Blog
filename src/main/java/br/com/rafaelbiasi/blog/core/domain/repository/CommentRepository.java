package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public interface CommentRepository {

	Optional<Comment> findById(long id);

	void delete(Comment comment);

	Comment save(Comment comment);

	Page<Comment> findAll(Pageable pageable);

}
