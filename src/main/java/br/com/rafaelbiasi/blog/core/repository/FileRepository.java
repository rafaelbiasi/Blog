package br.com.rafaelbiasi.blog.core.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface FileRepository {

	void copy(
			final InputStream inputStream,
			final Path resolve
	) throws IOException;

	void createDirectories() throws IOException;

	Path resolve(final String originalFilename);

}
