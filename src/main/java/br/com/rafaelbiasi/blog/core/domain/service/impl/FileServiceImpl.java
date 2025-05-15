package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.SimpleFile;
import br.com.rafaelbiasi.blog.core.domain.model.SimpleResource;
import br.com.rafaelbiasi.blog.core.domain.repository.FileRepository;
import br.com.rafaelbiasi.blog.core.domain.service.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	public FileServiceImpl(final FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public void init() throws IOException {
		fileRepository.createDirectories();
	}

	@Override
	public void save(final SimpleFile file) throws IOException {
		requireNonNull(file, "The File has a null value.");
		final String originalFilename = file.getOriginalFilename();
		requireNonNull(originalFilename, "The Original filename has a null value.");
		try (InputStream is = file.getInputStream()) {
			fileRepository.copy(is, fileRepository.resolve(originalFilename));
		}
	}

	@Override
	public Optional<SimpleResource> load(final String filename) throws IOException {
		requireNonNull(filename, "The requested filename has a null value.");
		Path path = fileRepository.resolve(filename);
		if (Files.exists(path) && Files.isReadable(path)) {
			return Optional.of(new SimpleResource(filename, Files.newInputStream(path)));
		}
		return Optional.empty();
	}
}