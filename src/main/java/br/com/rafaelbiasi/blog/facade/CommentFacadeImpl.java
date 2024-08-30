package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.service.CommentService;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static java.util.Objects.requireNonNull;

@Component
@Transactional
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService commentService;
    private final Transformer<CommentData, Comment> commentTransformer;

    public CommentFacadeImpl(
            CommentService commentService,
            @Qualifier("commentTransformer") Transformer<CommentData, Comment> commentTransformer) {
        this.commentService = commentService;
        this.commentTransformer = commentTransformer;
    }

    @Override
    public void save(CommentData comment, String postCode, Principal principal) {
        requireNonNull(comment, "The Comment has a null value.");
        requireNonNull(postCode, "The Post Code has a null value.");
        requireNonNull(principal, "The Principal has a null value.");
        comment.setAuthor(AccountData.builder().username(principal.getName()).build());
        comment.setPost(PostData.builder().code(postCode).build());
        commentService.save(commentTransformer.convert(comment));
    }

    @Override
    public void delete(String code) {
        Comment comment = commentService.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentService.delete(comment);
    }
}
