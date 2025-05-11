package br.com.rafaelbiasi.blog.domain.service.impl;

import br.com.rafaelbiasi.blog.domain.model.Post;
import br.com.rafaelbiasi.blog.domain.repository.PostRepository;
import br.com.rafaelbiasi.blog.domain.service.PostService;
import br.com.rafaelbiasi.blog.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwUserNotFound;
import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Optional<Post> findById(final long id) {
        return postRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #post.author.username == authentication.principal.username")
    public void delete(final Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post save(final Post post) {
        requireNonNull(post, "The Post has a null value.");
        userService.findOneByUsername(post.getAuthor().getUsername())
                .ifPresentOrElse(
                        post::setAuthor,
                        () -> throwUserNotFound(post.getAuthor().getUsername())
                );
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findAll(final Pageable pageable) {
        requireNonNull(pageable, "The Pageable has a null value.");
        return postRepository.findAll(pageable);
    }

}
