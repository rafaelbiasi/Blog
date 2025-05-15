package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import br.com.rafaelbiasi.blog.core.domain.model.SimpleFile;
import br.com.rafaelbiasi.blog.core.domain.model.SimpleResource;
import br.com.rafaelbiasi.blog.core.domain.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class FileFacadeImpl implements FileFacade {
	
	private final FileService fileService;

	@Override
	public Optional<SimpleResource> load(final String imageUri) {
		requireNonNull(imageUri, "The ImageURI has a null value.");
		try {
			return fileService.load(imageUri);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@SneakyThrows
	public void save(final SimpleFile file) {
		requireNonNull(file, "The SimpleFile has a null value.");
		fileService.save(file);
	}
}