package br.com.rafaelbiasi.blog.core.domain.service;

import br.com.rafaelbiasi.blog.core.domain.model.SimpleFile;
import br.com.rafaelbiasi.blog.core.domain.model.SimpleResource;

import java.io.IOException;
import java.util.Optional;

public interface FileService {

	void init() throws IOException;

	void save(final SimpleFile file) throws IOException;

	Optional<SimpleResource> load(final String filename) throws IOException;

}
