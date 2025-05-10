package br.com.rafaelbiasi.blog.domain.service;

import br.com.rafaelbiasi.blog.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface CommentService extends EntityService<Comment> {

    Optional<Comment> findByCode(final String code);

    Comment save(final Comment comment);

    Page<Comment> findAll(PageRequest pageable);
}
