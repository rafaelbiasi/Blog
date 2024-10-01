package br.com.rafaelbiasi.blog.domain.service;

import br.com.rafaelbiasi.blog.domain.entity.Comment;

import java.util.Optional;

public interface CommentService extends EntityService<Comment> {

    Optional<Comment> findByCode(final String code);

    Comment save(final Comment comment);
}
