package br.com.rafaelbiasi.blog.infrastructure.repository;

import br.com.rafaelbiasi.blog.domain.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BlogRepository<Comment> {

}
