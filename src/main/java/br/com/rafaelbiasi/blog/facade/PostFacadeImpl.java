package br.com.rafaelbiasi.blog.facade;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.service.PostService;
import br.com.rafaelbiasi.blog.transformer.Transformer;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;
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
        requireNonNull(pageable, "The Pageable has a null value.");
        return postService.findAll(pageable).map(postDataTransformer::convert);
    }

    @Override
    public Optional<PostData> findById(long id) {
        return postService.findById(id).map(postDataTransformer::convert);
    }

    @Override
    public void save(PostData postData) {
        requireNonNull(postData, "The Post has a null value.");
        ofNullable(postData.getCode())
                .flatMap(postService::findByCode)
                .ifPresentOrElse(post -> update(postData, post), () -> create(postData));
    }

    @Override
    public void save(PostData postData, Principal user) {
        requireNonNull(postData, "The Post has a null value.");
        requireNonNull(user, "The User has a null value.");
        postData.setAuthor(AccountData.builder().username(user.getName()).build());
        save(postData);
    }

    @Override
    public void save(PostData postData, MultipartFile file) throws IOException {
        requireNonNull(postData, "The Post has a null value.");
        requireNonNull(file, "The File has a null value.");
        Optional<String> originalFilename = of(file)
                .map(MultipartFile::getOriginalFilename)
                .filter(not(String::isBlank));
        originalFilename.ifPresent(postData::setImageFilePath);
        save(postData);
        originalFilename.ifPresent(s -> fileFacade.save(file));
    }

    @Override
    public void save(PostData postData, MultipartFile file, Principal user) throws IOException {
        requireNonNull(postData, "The Post has a null value.");
        requireNonNull(file, "The File has a null value.");
        requireNonNull(user, "The User has a null value.");
        postData.setAuthor(AccountData.builder().username(user.getName()).build());
        save(postData, file);
    }

    @Override
    public boolean delete(String code) {
        requireNonNull(code, "The Code has a null value.");
        Optional<Post> post = postService.findByCode(code);
        post.ifPresent(postService::delete);
        return post.isPresent();
    }

    @Override
    public Optional<PostData> findByCode(String code) {
        requireNonNull(code, "The Code has a null value.");
        return postService.findByCode(code).map(postDataTransformer::convert);
    }

    private void update(PostData postData, Post post) {
        postService.save(postTransformer.convertTo(postData, post));
    }

    private void create(PostData postData) {
        postService.save(postTransformer.convert(postData));
    }
}
