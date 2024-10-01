package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.CommentData;

import java.security.Principal;

public interface CommentFacade {
    void save(
            final CommentData comment,
            final String postCode,
            final Principal principal
    );

    void delete(final String code);
}
