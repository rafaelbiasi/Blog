package br.com.rafaelbiasi.blog.domain.service.impl;

import br.com.rafaelbiasi.blog.domain.model.Comment;
import br.com.rafaelbiasi.blog.domain.service.AccountService;
import br.com.rafaelbiasi.blog.domain.service.CommentService;
import br.com.rafaelbiasi.blog.domain.service.PostService;
import br.com.rafaelbiasi.blog.infrastructure.repository.CommentRepository;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwAccountNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwPostNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final PostService postService;

    @Override
    public Optional<Comment> findById(final long id) {
        return commentRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #comment.author.username == authentication.principal.username")
    public void delete(final Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Optional<Comment> findByCode(final String code) {
        return commentRepository.findById(SqidsUtil.decodeId(code));
    }

    @Override
    public Comment save(final Comment comment) {
        val username = comment.getAuthor().getUsername();
        val id = comment.getPost().getId();
        accountService.findOneByUsername(username)
                .ifPresentOrElse(comment::setAuthor, () -> throwAccountNotFound(username));
        postService.findById(id)
                .ifPresentOrElse(comment::setPost, () -> throwPostNotFound(id));
        return commentRepository.save(comment);
    }

}
