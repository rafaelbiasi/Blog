package br.com.rafaelbiasi.blog.facade.mapper.post;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.mapper.post.bidirectional.PostCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PostCodeMapper implements Mapper<PostData, Post> {

    private final PostCodeBidiMapper postCodeBidiMapper;

    public PostCodeMapper(PostCodeBidiMapper postCodeBidiMapper) {
        this.postCodeBidiMapper = postCodeBidiMapper;
    }

    @Override
    public void map(PostData source, Post target) throws ConversionException {
        postCodeBidiMapper.reverseMap(source, target);
    }
}
