package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.core.vo.SimpleFile;
import br.com.rafaelbiasi.blog.core.vo.SimpleResource;

import java.util.Optional;

public interface FileFacade {

	Optional<SimpleResource> load(final String imageUri);

	void save(final SimpleFile file);
}