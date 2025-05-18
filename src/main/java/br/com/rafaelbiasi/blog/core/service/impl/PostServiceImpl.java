package br.com.rafaelbiasi.blog.core.service.impl;

import br.com.rafaelbiasi.blog.core.model.Post;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.core.repository.PostRepository;
import br.com.rafaelbiasi.blog.core.service.PostService;
import br.com.rafaelbiasi.blog.core.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwUserNotFound;
import static java.util.Objects.requireNonNull;

public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public PostServiceImpl(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}

	@Override
	public Optional<Post> findById(final long id) {
		return postRepository.findById(id);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or #post.author.username == authentication.principal.username")
	//move PreAuthorize
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
	public SimplePage<Post> findAll(final SimplePageRequest pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return postRepository.findAll(pageable);
	}

}
