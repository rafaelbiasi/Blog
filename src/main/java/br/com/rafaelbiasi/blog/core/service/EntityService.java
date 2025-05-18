package br.com.rafaelbiasi.blog.core.service;

import java.util.Optional;

public interface EntityService<T> {

	Optional<T> findById(final long id);

	void delete(final T entity);

	T save(final T entity);
}
