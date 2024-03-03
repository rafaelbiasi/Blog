package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Comment;

import java.util.Optional;

public interface CommentService extends EntityService<Comment> {
    Optional<Comment> findByCode(String code);

    Comment save(Comment comment);
}
