package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<Comment> findByCode(String code) {
        return commentRepository.findByCode(code);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #comment.author.username == authentication.principal.username")
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment save(Comment comment) {
        comment.setAuthor(accountService.findOneByUsername(comment.getAuthor().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Account not found")));
        comment.setPost(postService.findByCode(comment.getPost().getCode())
                .orElseThrow(() -> new IllegalArgumentException("Post not found")));
        return commentRepository.save(comment);
    }
}
