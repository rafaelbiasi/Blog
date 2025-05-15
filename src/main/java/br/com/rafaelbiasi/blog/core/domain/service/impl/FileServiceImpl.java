package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.repository.FileRepository;
import br.com.rafaelbiasi.blog.core.domain.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
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
	public void save(final MultipartFile file) throws IOException {
		requireNonNull(file, "The File has a null value.");
		final String originalFilename = file.getOriginalFilename();
		requireNonNull(originalFilename, "The Original filename has a null value.");
		fileRepository.copy(file.getInputStream(), fileRepository.resolve(originalFilename));
	}

	@Override
	public Optional<Resource> load(final String filename) throws MalformedURLException {
		requireNonNull(filename, "The requested filename has a null value.");
		final Resource resource = fileRepository.getUrlResource(fileRepository.resolve(filename).toUri());
		if (resource.exists() || resource.isReadable()) {
			return Optional.of(resource);
		}
		return Optional.empty();
	}
}
