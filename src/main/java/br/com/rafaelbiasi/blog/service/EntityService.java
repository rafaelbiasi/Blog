package br.com.rafaelbiasi.blog.service;

import java.util.Optional;

/**
 * Defines a generic contract for entity services that provide basic operations on entities of type T.
 * This interface is intended to be a base for more specific service interfaces,
 * offering a common method for retrieving entities by their identifier.
 *
 * @param <T> the type of the entity managed by the service
 */
public interface EntityService<T> {

    /**
     * Retrieves an entity of type T by its identifier.
     * The method returns an {@link Optional} that encapsulates the entity.
     * If the entity with the specified identifier does not exist, the {@link Optional} will be empty.
     *
     * @param id the identifier of the entity to retrieve
     * @return an {@link Optional} containing the found entity or empty if no entity is found
     */
    Optional<T> getById(long id);
}
