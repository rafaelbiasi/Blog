package br.com.rafaelbiasi.blog.core.domain.repository;

import br.com.rafaelbiasi.blog.core.domain.model.CommentModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;

import java.util.Optional;

public interface CommentRepository {

	Optional<CommentModel> findById(long id);

	void delete(CommentModel comment);

	CommentModel save(CommentModel comment);

	PageModel<CommentModel> findAll(PageRequestModel pageable);

}
