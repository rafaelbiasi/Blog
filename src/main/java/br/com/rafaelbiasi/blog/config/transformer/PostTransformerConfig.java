package br.com.rafaelbiasi.blog.config.transformer;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.mapper.post.PostCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.post.PostDataCodeMapper;
import br.com.rafaelbiasi.blog.facade.mapper.post.PostDataMapper;
import br.com.rafaelbiasi.blog.facade.mapper.post.PostMapper;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PostTransformerConfig {

    private final PostDataMapper postDataMapper;
    private final PostMapper postMapper;
    private final PostDataCodeMapper postDataCodeMapper;
    private final PostCodeMapper postCodeMapper;

    public PostTransformerConfig(PostDataMapper postDataMapper, PostMapper postMapper, PostDataCodeMapper postDataCodeMapper, PostCodeMapper postCodeMapper) {
        this.postDataMapper = postDataMapper;
        this.postMapper = postMapper;
        this.postDataCodeMapper = postDataCodeMapper;
        this.postCodeMapper = postCodeMapper;
    }

    @Bean("postDataTransformer")
    public Transformer<Post, PostData> postDataTransformer() {
        Transformer<Post, PostData> transformer = new Transformer<>(PostData.class);
        transformer.setMappers(List.of(
                postDataCodeMapper,
                postDataMapper
        ));
        return transformer;
    }

    @Bean("postTransformer")
    public Transformer<PostData, Post> postTransformer() {
        Transformer<PostData, Post> postPostDataTransformer = new Transformer<>(Post.class);
        postPostDataTransformer.setMappers(List.of(
                postCodeMapper,
                postMapper
        ));
        return postPostDataTransformer;
    }

    @Bean("postDataCodeTransformer")
    public Transformer<Post, PostData> postDataCodeTransformer() {
        Transformer<Post, PostData> transformer = new Transformer<>(PostData.class);
        transformer.setMappers(List.of(postDataCodeMapper));
        return transformer;
    }

    @Bean("postCodeTransformer")
    public Transformer<PostData, Post> postCodeTransformer() {
        Transformer<PostData, Post> postPostDataTransformer = new Transformer<>(Post.class);
        postPostDataTransformer.setMappers(List.of(postCodeMapper));
        return postPostDataTransformer;
    }
}
