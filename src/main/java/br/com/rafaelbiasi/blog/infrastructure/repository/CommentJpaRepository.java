package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.model.Comment;
import br.com.rafaelbiasi.blog.domain.repository.CommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends BlogRepository<Comment>, CommentRepository {

}
