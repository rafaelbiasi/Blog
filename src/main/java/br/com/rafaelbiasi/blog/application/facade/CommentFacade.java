package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;

import java.security.Principal;
import java.util.Optional;

public interface CommentFacade {
	void save(
			final CommentData comment,
			final String postCode,
			final Principal principal
	);

	boolean delete(final String code);

	PageModel<CommentData> findAll(PageRequestModel pageable);

	Optional<CommentData> findByCode(String code);
}
