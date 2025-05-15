package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.PostModel;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

	Optional<PostModel> findById(long id);

	void delete(PostModel post);

	PostModel save(PostModel post);

	List<PostModel> findAll();

	PageModel<PostModel> findAll(PageRequestModel pageable);
}
