package br.com.rafaelbiasi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * A base repository interface combining the capabilities of {@link JpaRepository}
 * and {@link PagingAndSortingRepository} for entities of type T.
 * This interface is marked with {@link NoRepositoryBean} to indicate that Spring Data JPA
 * should not create an implementation instance at runtime. Instead, it serves as a base
 * for other repository interfaces for specific entity types.
 * <p>
 * By extending both {@link JpaRepository} and {@link PagingAndSortingRepository},
 * it provides CRUD operations, pagination, and sorting capabilities for entity management.
 *
 * @param <T> the domain type the repository manages
 */
@NoRepositoryBean
public interface BlogRepository<T> extends JpaRepository<T, Long>, PagingAndSortingRepository<T, Long> {
}
