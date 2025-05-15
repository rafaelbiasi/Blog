package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.core.domain.model.SimpleFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostFacade {

	List<PostData> findAll();

	Page<PostData> findAll(final Pageable pageable);

	Optional<PostData> findById(final long id);

	PostData save(final PostData post);

	PostData save(
			final PostData post,
			final SimpleFile file
	);

	boolean delete(final String code);

	Optional<PostData> findByCode(final String code);
}
