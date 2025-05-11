package br.com.rafaelbiasi.blog.domain.repository;

import br.com.rafaelbiasi.blog.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CommentRepository {

	Optional<Comment> findById(long id);

	void delete(Comment comment);

	Comment save(Comment comment);

	Page<Comment> findAll(Pageable pageable);

}
