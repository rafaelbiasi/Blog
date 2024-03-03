package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.CommentData;

import java.security.Principal;

public interface CommentFacade {
    void save(CommentData comment, String postCode, Principal principal);

    void delete(String code);
}
