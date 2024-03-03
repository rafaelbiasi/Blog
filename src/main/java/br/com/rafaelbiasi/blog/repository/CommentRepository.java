package br.com.rafaelbiasi.blog.repository;

import br.com.rafaelbiasi.blog.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends BlogRepository<Comment> {

    Optional<Comment> findByCode(String code);
}
