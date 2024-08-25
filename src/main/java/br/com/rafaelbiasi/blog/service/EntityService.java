package br.com.rafaelbiasi.blog.service;

import java.util.Optional;

public interface EntityService<T> {

    default void delete(long id) {
        findById(id).ifPresent(this::delete);
    }

    Optional<T> findById(long id);

    void delete(T entity);

    T save(T entity);
}
