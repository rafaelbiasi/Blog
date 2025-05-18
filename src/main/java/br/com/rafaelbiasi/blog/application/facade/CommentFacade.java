package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.security.Principal;
import java.util.Optional;

public interface CommentFacade {
	CommentData save(
			final CommentData comment,
			final String postCode,
			final Principal principal
	);

	CommentData save(CommentData comment);

	boolean delete(final String code);

	SimplePage<CommentData> findAll(SimplePageRequest pageable);

	Optional<CommentData> findByCode(String code);
}
