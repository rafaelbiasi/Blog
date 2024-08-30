package br.com.rafaelbiasi.blog.facade.mapper.post;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.exception.ConversionException;
import br.com.rafaelbiasi.blog.facade.mapper.post.bidirectional.PostBidiMapper;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PostDataMapper implements Mapper<Post, PostData> {

    private final PostBidiMapper postBidiMapper;

    public PostDataMapper(PostBidiMapper postBidiMapper) {
        this.postBidiMapper = postBidiMapper;
    }

    @Override
    public void map(Post source, PostData target) throws ConversionException {
        postBidiMapper.map(source, target);
    }
}
