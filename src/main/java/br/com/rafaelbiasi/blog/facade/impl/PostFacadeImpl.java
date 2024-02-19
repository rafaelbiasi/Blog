package br.com.rafaelbiasi.blog.facade.impl;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.service.PostService;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;

    private final Transformer<Post, PostData> postDataTransformer;

    private final Transformer<PostData, Post> postTransformer;

    public PostFacadeImpl(
            PostService postService,
            @Qualifier("postDataTransformer") Transformer<Post, PostData> postDataTransformer,
            @Qualifier("postTransformer") Transformer<PostData, Post> postTransformer
    ) {
        this.postService = postService;
        this.postDataTransformer = postDataTransformer;
        this.postTransformer = postTransformer;
    }

    @Override
    public List<PostData> getAll() {
        return postDataTransformer.convertAll(postService.getAll());
    }

    @Override
    public Page<PostData> getAll(Pageable pageable) {
        Objects.requireNonNull(pageable, "Pageable is null.");
        return postService.getAll(pageable).map(postDataTransformer::convert);
    }

    @Override
    public Optional<PostData> getById(long id) {
        return postService.getById(id).map(postDataTransformer::convert);
    }

    @Override
    public void save(PostData postData) {
        Objects.requireNonNull(postData, "AccountData is null.");
        postService.getByCode(postData.getCode())
                .ifPresentOrElse(post -> postService.save(postTransformer.convertTo(postData, post)),
                        () -> postService.save(postTransformer.convert(postData)));
    }

    @Override
    public void delete(PostData postData) {
        Objects.requireNonNull(postData, "PostData is null.");
        postService.getByCode(postData.getCode())
                .ifPresent(post -> postService.delete(postTransformer.convertTo(postData, post)));
    }

    @Override
    public Optional<PostData> getByCode(String code) {
        Objects.requireNonNull(code, "Code is null.");
        return postService.getByCode(code).map(postDataTransformer::convert);
    }
}
