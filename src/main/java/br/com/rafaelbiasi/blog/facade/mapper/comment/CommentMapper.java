package br.com.rafaelbiasi.blog.facade.mapper.comment;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.facade.mapper.comment.bidirectional.CommentBidiMapper;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper implements Mapper<CommentData, Comment> {

    private final CommentBidiMapper commentBidiMapper;

    @Override
    public void map(CommentData source, Comment target) throws ConversionException {
        commentBidiMapper.reverseMap(source, target);
    }
}
