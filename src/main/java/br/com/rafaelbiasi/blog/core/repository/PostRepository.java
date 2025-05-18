package br.com.rafaelbiasi.blog.core.repository;

import br.com.rafaelbiasi.blog.core.model.Post;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

	Optional<Post> findById(long id);

	void delete(Post post);

	Post save(Post post);

	List<Post> findAll();

	SimplePage<Post> findAll(SimplePageRequest pageable);
}
