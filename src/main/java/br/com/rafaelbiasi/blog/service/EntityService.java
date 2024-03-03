package br.com.rafaelbiasi.blog.service;

import java.util.Optional;

public interface EntityService<T> {

    Optional<T> findById(long id);

    default void delete(long id) {
        findById(id).ifPresent(this::delete);
    }

    void delete(T entity);

    T save(T entity);
}
