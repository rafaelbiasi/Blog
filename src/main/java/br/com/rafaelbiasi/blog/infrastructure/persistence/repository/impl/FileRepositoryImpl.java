package br.com.rafaelbiasi.blog.infrastructure.persistence.repository.impl;

import br.com.rafaelbiasi.blog.core.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

	private final Path root;

	public FileRepositoryImpl() {
		this(Paths.get("./uploads"));
	}

	@Override
	public void copy(
			final InputStream inputStream,
			final Path target
	) throws IOException {
		Files.copy(inputStream, target);
	}

	@Override
	public void createDirectories() throws IOException {
		Files.createDirectories(root);
	}

	@Override
	public Path resolve(final String originalFilename) {
		return root.resolve(originalFilename);
	}

}
