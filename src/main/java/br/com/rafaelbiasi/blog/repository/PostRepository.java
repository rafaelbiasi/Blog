package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Extends the {@link BlogRepository} to provide repository operations specifically for {@link Post} entities.
 * This interface declares additional methods tailored to the needs of managing blog posts, such as finding a post by its unique code.
 * The {@link Repository} annotation marks it as a Spring-managed Data Access Object, allowing Spring Data JPA to create proxy instances at runtime.
 */
@Repository
public interface PostRepository extends BlogRepository<Post> {

    /**
     * Finds a {@link Post} entity by its unique code.
     * This method facilitates the retrieval of posts based on a business- or domain-specific identifier,
     * separate from the primary database key.
     *
     * @param code the unique code of the post to find
     * @return an {@link Optional} containing the found {@link Post} if available, or empty otherwise
     */
    Optional<Post> findByCode(String code);
}
