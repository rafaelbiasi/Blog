package br.com.rafaelbiasi.blog.core.service.impl;

import br.com.rafaelbiasi.blog.core.model.Comment;
import br.com.rafaelbiasi.blog.core.repository.CommentRepository;
import br.com.rafaelbiasi.blog.core.service.CommentService;
import br.com.rafaelbiasi.blog.core.service.PostService;
import br.com.rafaelbiasi.blog.core.service.UserService;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;

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
	public Optional<Comment> findById(final long id) {
		return commentRepository.findById(id);
	}

	@Override
	public void delete(final Comment comment) {
		commentRepository.delete(comment);
	}

	@Override
	public Optional<Comment> findByCode(final String code) {
		return commentRepository.findById(SqidsUtil.decodeId(code));
	}

	@Override
	public Comment save(final Comment comment) {
		final String username = comment.getAuthor().getUsername();
		final Long id = comment.getPost().getId();
		userService.findOneByUsername(username)
				.ifPresentOrElse(comment::setAuthor, () -> throwUserNotFound(username));
		postService.findById(id)
				.ifPresentOrElse(comment::setPost, () -> throwPostNotFound(id));
		return commentRepository.save(comment);
	}

	@Override
	public SimplePage<Comment> findAll(SimplePageRequest pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return commentRepository.findAll(pageable);
	}

}
