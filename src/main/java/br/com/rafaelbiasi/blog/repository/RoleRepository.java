package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Defines the repository interface for {@link Role} entities.
 * This interface extends {@link BlogRepository} for common CRUD operations
 * and includes a method for finding an {@link Role} by its name.
 * The {@link Repository} annotation indicates that this is a Spring-managed repository component,
 * eligible for Spring Data repository infrastructure to automatically implement.
 */
@Repository
public interface RoleRepository extends BlogRepository<Role> {

    /**
     * Finds an {@link Role} entity by its name.
     * This method is useful for retrieving roles based on their unique names,
     * typically representing roles or permissions within the system.
     *
     * @param name the name of the {@link Role} to find
     * @return an {@link Optional} containing the found {@link Role}, or empty if no role with the given name exists
     */
    Optional<Role> findByName(String name);
}
