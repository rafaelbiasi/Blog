package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends BlogRepository<Post> {

    Optional<Post> findByCode(String code);
}
