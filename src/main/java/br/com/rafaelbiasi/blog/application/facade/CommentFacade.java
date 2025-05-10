package br.com.rafaelbiasi.blog.application.facade;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;
import java.util.Optional;

public interface CommentFacade {
    void save(
            final CommentData comment,
            final String postCode,
            final Principal principal
    );

    boolean delete(final String code);

    Page<CommentData> findAll(PageRequest pageable);

    Optional<CommentData> findByCode(String code);
}
