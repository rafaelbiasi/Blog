package br.com.rafaelbiasi.blog.config.transformer;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.facade.mapper.comment.CommentCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.comment.CommentDataCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.comment.CommentDataMapper;
import br.com.rafaelbiasi.blog.facade.mapper.comment.CommentMapper;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CommentTransformerConfig {

    private final CommentDataMapper commentDataMapper;
    private final CommentMapper commentMapper;
    private final CommentDataCodeMapper commentDataCodeMapper;
    private final CommentCodeMapper commentCodeMapper;

    @Bean("commentDataTransformer")
    public Transformer<Comment, CommentData> commentDataTransformer() {
        Transformer<Comment, CommentData> transformer = new Transformer<>(CommentData.class);
        transformer.setMappers(List.of(
                commentDataCodeMapper,
                commentDataMapper
        ));
        return transformer;
    }

    @Bean("commentTransformer")
    public Transformer<CommentData, Comment> commentTransformer() {
        Transformer<CommentData, Comment> transformer = new Transformer<>(Comment.class);
        transformer.setMappers(List.of(
                commentCodeMapper,
                commentMapper
        ));
        return transformer;
    }
}