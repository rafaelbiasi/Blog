package br.com.rafaelbiasi.blog.facade.mapper.comment;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.exception.ConversionException;
import br.com.rafaelbiasi.blog.facade.mapper.comment.bidirectional.CommentCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDataCodeMapper implements Mapper<Comment, CommentData> {

    private final CommentCodeBidiMapper commentCodeBidiMapper;

    @Override
    public void map(Comment source, CommentData target) throws ConversionException {
        commentCodeBidiMapper.map(source, target);
    }
}
