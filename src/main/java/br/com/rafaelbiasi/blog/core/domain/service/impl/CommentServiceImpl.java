package br.com.rafaelbiasi.blog.core.domain.service.impl;

import br.com.rafaelbiasi.blog.core.domain.model.CommentModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageModel;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.repository.CommentRepository;
import br.com.rafaelbiasi.blog.core.domain.service.CommentService;
import br.com.rafaelbiasi.blog.core.domain.service.PostService;
import br.com.rafaelbiasi.blog.core.domain.service.UserService;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwPostNotFound;
import static br.com.rafaelbiasi.blog.infrastructure.exception.ResourceNotFoundExceptionFactory.throwUserNotFound;
import static java.util.Objects.requireNonNull;

public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final UserService userService;
	private final PostService postService;

	public CommentServiceImpl(final CommentRepository commentRepository,
							  final UserService userService,
							  final PostService postService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	@Override
	public Optional<CommentModel> findById(final long id) {
		return commentRepository.findById(id);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or #comment.author.username == authentication.principal.username")
	//move PreAuthorize
	public void delete(final CommentModel comment) {
		commentRepository.delete(comment);
	}

	@Override
	public Optional<CommentModel> findByCode(final String code) {
		return commentRepository.findById(SqidsUtil.decodeId(code));
	}

	@Override
	public CommentModel save(final CommentModel comment) {
		final String username = comment.getAuthor().getUsername();
		final Long id = comment.getPost().getId();
		userService.findOneByUsername(username)
				.ifPresentOrElse(comment::setAuthor, () -> throwUserNotFound(username));
		postService.findById(id)
				.ifPresentOrElse(comment::setPost, () -> throwPostNotFound(id));
		return commentRepository.save(comment);
	}

	@Override
	public PageModel<CommentModel> findAll(PageRequestModel pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return commentRepository.findAll(pageable);
	}

}
