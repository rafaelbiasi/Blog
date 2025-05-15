package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.PostModel;
import br.com.rafaelbiasi.blog.core.domain.repository.PostRepository;
import br.com.rafaelbiasi.blog.core.domain.service.PostService;
import br.com.rafaelbiasi.blog.core.domain.service.UserService;
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
	public Optional<PostModel> findById(final long id) {
		return postRepository.findById(id);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or #post.author.username == authentication.principal.username")
	//move PreAuthorize
	public void delete(final PostModel post) {
		postRepository.delete(post);
	}

	@Override
	public PostModel save(final PostModel post) {
		requireNonNull(post, "The Post has a null value.");
		userService.findOneByUsername(post.getAuthor().getUsername())
				.ifPresentOrElse(
						post::setAuthor,
						() -> throwUserNotFound(post.getAuthor().getUsername())
				);
		return postRepository.save(post);
	}

	@Override
	public List<PostModel> findAll() {
		return postRepository.findAll();
	}

	@Override
	public PageModel<PostModel> findAll(final PageRequestModel pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return postRepository.findAll(pageable);
	}

}
