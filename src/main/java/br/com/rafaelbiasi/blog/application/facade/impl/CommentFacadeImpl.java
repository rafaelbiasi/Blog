package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.facade.CommentFacade;
import br.com.rafaelbiasi.blog.application.data.AccountData;
import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.mapper.CommentMapper;
import br.com.rafaelbiasi.blog.domain.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwCommentNotFound;
import static java.util.Objects.requireNonNull;

@Component
@Transactional
@RequiredArgsConstructor
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Override
    public void save(
            final CommentData comment,
            final String postCode,
            final Principal principal
    ) {
        requireNonNull(comment, "The Comment has a null value.");
        requireNonNull(postCode, "The Post Code has a null value.");
        requireNonNull(principal, "The Principal has a null value.");
        comment.setAuthor(
                AccountData.builder()
                        .username(principal.getName())
                        .build());
        comment.setPost(PostData.builder().code(postCode).build());
        commentService.save(commentMapper.commentDataToComment(comment));
    }

    @Override
    public void delete(final String code) {
        commentService.findByCode(code).ifPresentOrElse(
                commentService::delete,
                () -> throwCommentNotFound(code)
        );
    }
}
