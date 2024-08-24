package br.com.rafaelbiasi.blog.facade.mapper.post;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.mapper.post.bidirectional.PostCodeBidiMapper;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.Mapper;
import br.com.rafaelbiasi.blog.transformer.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class PostDataCodeMapper implements Mapper<Post, PostData> {

    private final PostCodeBidiMapper postCodeBidiMapper;

    public PostDataCodeMapper(PostCodeBidiMapper postCodeBidiMapper) {
        this.postCodeBidiMapper = postCodeBidiMapper;
    }

    @Override
    public void map(Post source, PostData target) throws ConversionException {
        postCodeBidiMapper.map(source, target);
    }
}
