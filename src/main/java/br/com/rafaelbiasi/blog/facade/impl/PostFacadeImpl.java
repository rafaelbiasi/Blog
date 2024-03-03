package br.com.rafaelbiasi.blog.facade.impl;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.FileFacade;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.service.PostService;
import br.com.rafaelbiasi.blog.transformer.impl.Transformer;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Slf4j
@Component
@Transactional
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;
    private final FileFacade fileFacade;
    private final Transformer<Post, PostData> postDataTransformer;
    private final Transformer<PostData, Post> postTransformer;

    public PostFacadeImpl(
            PostService postService,
            FileFacade fileFacade,
            @Qualifier("postDataTransformer") Transformer<Post, PostData> postDataTransformer,
            @Qualifier("postTransformer") Transformer<PostData, Post> postTransformer) {
        this.postService = postService;
        this.fileFacade = fileFacade;
        this.postDataTransformer = postDataTransformer;
        this.postTransformer = postTransformer;
    }

    @Override
    public List<PostData> findAll() {
        return postDataTransformer.convertAll(postService.findAll());
    }

    @Override
    public Page<PostData> findAll(Pageable pageable) {
        requireNonNull(pageable, "Pageable is null.");
        return postService.findAll(pageable).map(postDataTransformer::convert);
    }

    @Override
    public Optional<PostData> findById(long id) {
        return postService.findById(id).map(postDataTransformer::convert);
    }

    @Override
    public void save(PostData postData) {
        requireNonNull(postData, "Post is null.");
        ofNullable(postData.getCode())
                .flatMap(postService::findByCode)
                .ifPresentOrElse(post -> update(postData, post), () -> create(postData));
    }

    @Override
    public void save(PostData postData, MultipartFile file) {
        requireNonNull(postData, "Post is null.");
        requireNonNull(file, "File is null.");
        Optional<String> originalFilename = ofNullable(file.getOriginalFilename()).filter(not(String::isBlank));
        originalFilename.ifPresent(postData::setImageFilePath);
        save(postData);
        if (originalFilename.isPresent()) {
            try {
                fileFacade.save(file);
            } catch (Exception e) {
                log.error("Error processing file: {}", originalFilename, e);
                throw e;
            }
        }
    }

    @Override
    public void save(PostData postData, Principal user) {
        requireNonNull(postData, "Post is null.");
        requireNonNull(user, "User is null.");
        postData.setAuthor(AccountData.builder().username(user.getName()).build());
        save(postData);
    }

    @Override
    public void save(PostData postData, MultipartFile file, Principal user) {
        requireNonNull(postData, "Post is null.");
        requireNonNull(file, "File is null.");
        requireNonNull(user, "User is null.");
        postData.setAuthor(AccountData.builder().username(user.getName()).build());
        save(postData, file);
    }

    @Override
    public void delete(String code) {
        requireNonNull(code, "Code is null.");
        Post post = postService.findByCode(code).orElseThrow(() -> new RuntimeException("Code not found."));
        postService.delete(post);
    }

    @Override
    public Optional<PostData> findByCode(String code) {
        requireNonNull(code, "Code is null.");
        return postService.findByCode(code).map(postDataTransformer::convert);
    }

    private void create(PostData postData) {
        postService.save(postTransformer.convert(postData));
    }

    private void update(PostData postData, Post post) {
        postService.save(postTransformer.convertTo(postData, post));
    }
}
