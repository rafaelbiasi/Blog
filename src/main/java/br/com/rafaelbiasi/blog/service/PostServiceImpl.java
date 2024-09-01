package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Post;
import br.com.rafaelbiasi.blog.repository.PostRepository;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.rafaelbiasi.blog.exception.ResourceNotFoundExceptionFactory.throwAccountNotFound;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AccountService accountService;
    private final Slugify slugify = Slugify.builder().build();

    @Override
    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #post.author.username == authentication.principal.username")
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post save(Post post) {
        requireNonNull(post, "The Post has a null value.");
        accountService.findOneByUsername(post.getAuthor().getUsername())
                .ifPresentOrElse(
                        post::setAuthor,
                        () -> throwAccountNotFound(post.getAuthor().getUsername())
                );
        //TODO: verificar se o cdigo slugify já existe.
        of(post).map(Post::getCode)
                .ifPresentOrElse(
                        code -> {
                        }, () -> post.setCode(slugify.slugify(post.getTitle())));
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        requireNonNull(pageable, "The Pageable has a null value.");
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findByCode(String code) {
        requireNonNull(code, "The Code has a null value.");
        return postRepository.findByCode(code);
    }

}
