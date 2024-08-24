package br.com.rafaelbiasi.blog.facade.mapper.comment;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.facade.mapper.comment.bidirectional.CommentCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCodeMapper implements Mapper<CommentData, Comment> {

    private final CommentCodeBidiMapper commentCodeBidiMapper;

    @Override
    public void map(CommentData source, Comment target) throws ConversionException {
        commentCodeBidiMapper.reverseMap(source, target);
    }
}
