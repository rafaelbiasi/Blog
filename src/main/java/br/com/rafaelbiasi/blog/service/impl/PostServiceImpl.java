package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.repository.PostRepository;
import br.com.rafaelbiasi.blog.service.PostService;
import br.com.rafaelbiasi.blog.specification.impl.IsNewPostSpecification;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final IsNewPostSpecification isNewPostSpecification;
    private final Slugify slugify = new Slugify();


    @Override
    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        Objects.requireNonNull(pageable, "Pageable is null.");
        return postRepository.findAll(pageable);
    }

    @Override
    public Post save(Post post) {
        Objects.requireNonNull(post, "Post is null.");
        if (isNewPostSpecification.isSatisfiedBy(post)) {
            post.setCode(slugify.slugify(post.getTitle()));
        }
        return postRepository.save(post);
    }

    @Override
    public void delete(String code) {
        Objects.requireNonNull(code, "Post is null.");
        postRepository.delete(postRepository.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Post not found")));
    }

    @Override
    public Optional<Post> findByCode(String code) {
        Objects.requireNonNull(code, "Code is null.");
        return postRepository.findByCode(code);
    }

}
