package br.com.rafaelbiasi.blog.core.service;

import br.com.rafaelbiasi.blog.core.vo.SimpleFile;
import br.com.rafaelbiasi.blog.core.vo.SimpleResource;

import java.io.IOException;
import java.util.Optional;

public interface FileService {

	void init() throws IOException;

	void save(final SimpleFile file) throws IOException;

	Optional<SimpleResource> load(final String filename) throws IOException;

}
