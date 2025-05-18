package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.core.vo.SimpleFile;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;

import java.util.List;
import java.util.Optional;

public interface PostFacade {

	List<PostData> findAll();

	SimplePage<PostData> findAll(final SimplePageRequest pageable);

	Optional<PostData> findById(final long id);

	PostData save(final PostData post);

	PostData save(
			final PostData post,
			final SimpleFile file
	);

	boolean delete(final String code);

	Optional<PostData> findByCode(final String code);
}
