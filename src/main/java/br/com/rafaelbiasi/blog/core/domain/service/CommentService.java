package br.com.rafaelbiasi.blog.core.domain.service;

import br.com.rafaelbiasi.blog.core.domain.model.CommentModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;

import java.util.Optional;

public interface CommentService extends EntityService<CommentModel> {

	Optional<CommentModel> findByCode(final String code);

	CommentModel save(final CommentModel comment);

	PageModel<CommentModel> findAll(PageRequestModel pageable);
}
