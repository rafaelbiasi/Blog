package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

	Optional<Post> findById(long id);

	void delete(Post post);

	Post save(Post post);

	List<Post> findAll();

	Page<Post> findAll(Pageable pageable);
}
