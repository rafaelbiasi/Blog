package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.rafaelbiasi.blog.exception.ResourceNotFoundExceptionFactory.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final PostService postService;

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #comment.author.username == authentication.principal.username")
    public void delete(Comment comment) {
        findByCode(comment.getCode())
                .ifPresentOrElse(commentRepository::delete, () -> throwCommentNotFound(comment.getCode()));
    }

    @Override
    public Optional<Comment> findByCode(String code) {
        return commentRepository.findByCode(code);
    }

    @Override
    public Comment save(Comment comment) {
        String username = comment.getAuthor().getUsername();
        String code = comment.getPost().getCode();
        accountService.findOneByUsername(username)
                .ifPresentOrElse(comment::setAuthor, () -> throwAccountNotFound(username));
        postService.findByCode(code)
                .ifPresentOrElse(comment::setPost, () -> throwPostNoFound(code));
        return commentRepository.save(comment);
    }


}
