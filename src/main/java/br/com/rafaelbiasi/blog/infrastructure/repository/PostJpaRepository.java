package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.model.Post;
import br.com.rafaelbiasi.blog.domain.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends BlogRepository<Post>, PostRepository {

}
