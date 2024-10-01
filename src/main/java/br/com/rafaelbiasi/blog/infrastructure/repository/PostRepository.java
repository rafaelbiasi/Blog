package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BlogRepository<Post> {

}
