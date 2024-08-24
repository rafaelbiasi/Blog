package br.com.rafaelbiasi.blog.facade.mapper.post.bidirectional;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.BidirectionalMapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class PostCodeBidiMapper implements BidirectionalMapper<Post, PostData> {

    @Override
    public void reverseMap(PostData source, Post target) throws ConversionException {
        mapGet(source::getCode, target::setCode);
    }

    @Override
    public void map(Post source, PostData target) throws ConversionException {
        mapGet(source::getCode, target::setCode);
    }
}
