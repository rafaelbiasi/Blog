package br.com.rafaelbiasi.blog.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BlogRepository<T> extends JpaRepository<T, Long>, PagingAndSortingRepository<T, Long> {
}
