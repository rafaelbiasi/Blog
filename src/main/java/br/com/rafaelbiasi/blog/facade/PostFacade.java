package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.PostData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for the PostFacade, which provides a high-level interface for blog post operations.
 * This facade abstracts the complexities of interacting with underlying services or repositories, offering
 * simplified methods for fetching, saving, and deleting posts. It is designed to facilitate the interaction
 * between the application's front-end and the service layer, ensuring a clear separation of concerns.
 */
public interface PostFacade {

    /**
     * Retrieves all blog posts.
     * This method is intended for use cases where a complete list of posts is required, without pagination.
     *
     * @return a {@link List} of {@link PostData} representing all blog posts
     */
    List<PostData> getAll();

    /**
     * Retrieves a paginated list of blog posts.
     * This method supports pagination and sorting, making it suitable for scenarios where posts need to be
     * displayed in a paginated format, such as in web interfaces or APIs.
     *
     * @param pageable a {@link Pageable} instance specifying the pagination and sorting criteria
     * @return a {@link Page} of {@link PostData} representing the paginated blog posts
     */
    Page<PostData> getAll(Pageable pageable);

    /**
     * Retrieves a blog post by its unique identifier.
     * This method facilitates fetching specific posts for viewing or editing.
     *
     * @param id the unique identifier of the post to retrieve
     * @return an {@link Optional} containing the {@link PostData} if found, or empty otherwise
     */
    Optional<PostData> getById(long id);

    /**
     * Saves or updates a blog post.
     * This method can be used for both creating new posts and updating existing ones, based on the presence
     * of an identifier in the {@link PostData} object.
     *
     * @param post the {@link PostData} containing the blog post information to save or update
     */
    void save(PostData post);

    /**
     * Deletes a given blog post.
     * This method removes the post from the system, along with any associated data.
     *
     * @param code the unique code of the post to delete
     */
    void delete(String code);

    /**
     * Retrieves a blog post by its unique code.
     * This method allows for fetching posts based on a business- or domain-specific identifier,
     * separate from the primary database key.
     *
     * @param code the unique code of the post to retrieve
     * @return an {@link Optional} containing the {@link PostData} if found, or empty otherwise
     */
    Optional<PostData> getByCode(String code);
}
