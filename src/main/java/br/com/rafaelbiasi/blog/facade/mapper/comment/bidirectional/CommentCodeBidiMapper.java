package br.com.rafaelbiasi.blog.facade.mapper.comment.bidirectional;

import br.com.rafaelbiasi.blog.data.CommentData;
import br.com.rafaelbiasi.blog.model.Comment;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class CommentCodeBidiMapper implements BidirectionalMapper<Comment, CommentData> {

    @Override
    public void map(Comment source, CommentData target) throws ConversionException {
        mapGet(source::getCode, target::setCode);
    }

    @Override
    public void reverseMap(CommentData source, Comment target) throws ConversionException {
        mapGet(source::getCode, target::setCode);
    }
}
