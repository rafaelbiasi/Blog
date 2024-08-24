package br.com.rafaelbiasi.blog.facade.mapper.post;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.mapper.post.bidirectional.PostBidiMapper;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper implements Mapper<PostData, Post> {

    private final PostBidiMapper postBidiMapper;

    public PostMapper(PostBidiMapper postBidiMapper) {
        this.postBidiMapper = postBidiMapper;
    }

    @Override
    public void map(PostData source, Post target) throws ConversionException {
        postBidiMapper.reverseMap(source, target);
    }
}
