package br.com.rafaelbiasi.blog.domain.service;

import java.util.Optional;

public interface EntityService<T> {

    default void delete(final long id) {
        findById(id).ifPresent(this::delete);
    }

    Optional<T> findById(final long id);

    void delete(final T entity);

    T save(final T entity);
}
