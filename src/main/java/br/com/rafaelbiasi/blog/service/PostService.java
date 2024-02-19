package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for services managing {@link Post} entities.
 * This interface extends {@link EntityService} to provide specific operations for posts,
 * such as retrieving all posts, paginated retrieval of posts, saving a post, deleting a post,
 * and finding a post by its unique code.
 */
public interface PostService extends EntityService<Post> {

    /**
     * Retrieves all {@link Post} entities.
     * This method is intended for use cases where all posts need to be fetched from the database.
     *
     * @return a {@link List} of {@link Post} entities
     */
    List<Post> getAll();

    /**
     * Retrieves a page of {@link Post} entities according to the given {@link Pageable} object.
     * This method supports pagination and sorting of the posts.
     *
     * @param pageable a {@link Pageable} instance specifying the pagination and sorting criteria
     * @return a {@link Page} of {@link Post} entities
     */
    Page<Post> getAll(Pageable pageable);

    /**
     * Saves a {@link Post} entity to the persistent store.
     * This method can be used for creating new posts or updating existing ones.
     * Implementations should ensure that the post is persisted with correct handling of any associated entities.
     *
     * @param post the {@link Post} entity to save
     * @return the saved {@link Post} entity, including any generated or modified fields
     */
    Post save(Post post);

    /**
     * Deletes a given {@link Post} entity.
     * Implementations should ensure that the post, along with any associated entities, is properly removed
     * from the persistent store.
     *
     * @param post the {@link Post} entity to delete
     */
    void delete(Post post);

    /**
     * Retrieves a {@link Post} entity by its unique code.
     * The method returns an {@link Optional} that will be empty if no post with the specified code exists.
     *
     * @param code the unique code of the post to retrieve
     * @return an {@link Optional} containing the found {@link Post} or empty if none found
     */
    Optional<Post> getByCode(String code);
}
