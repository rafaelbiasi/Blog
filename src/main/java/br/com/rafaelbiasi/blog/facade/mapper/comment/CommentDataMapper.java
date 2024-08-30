package br.com.rafaelbiasi.blog.facade.mapper.comment;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.exception.ConversionException;
import br.com.rafaelbiasi.blog.facade.mapper.comment.bidirectional.CommentBidiMapper;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDataMapper implements Mapper<Comment, CommentData> {

    private final CommentBidiMapper commentBidiMapper;

    @Override
    public void map(Comment source, CommentData target) throws ConversionException {
        commentBidiMapper.map(source, target);
    }
}
