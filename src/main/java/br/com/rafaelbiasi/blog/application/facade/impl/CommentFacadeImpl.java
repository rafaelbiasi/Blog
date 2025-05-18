package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.application.facade.CommentFacade;
import br.com.rafaelbiasi.blog.application.mapper.CommentMapper;
import br.com.rafaelbiasi.blog.core.model.Comment;
import br.com.rafaelbiasi.blog.core.service.CommentService;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Component
@Transactional
@RequiredArgsConstructor
public class CommentFacadeImpl implements CommentFacade {

	private final CommentService commentService;
	private final CommentMapper commentMapper;

	@Override
	public CommentData save(
			final CommentData commentData,
			final String postCode,
			final Principal principal
	) {
		requireNonNull(commentData, "The Comment has a null value.");
		requireNonNull(postCode, "The Post Code has a null value.");
		requireNonNull(principal, "The Principal has a null value.");
		commentData.setAuthor(
				UserData.builder()
						.username(principal.getName())
						.build());
		commentData.setPost(PostData.builder().code(postCode).build());
		return save(commentData);
	}

	@Override
	public CommentData save(final CommentData commentData) {
		requireNonNull(commentData, "The Comment has a null value.");
		return ofNullable(commentData.getCode())
				.map(SqidsUtil::decodeId)
				.flatMap(commentService::findById)
				.map(comment -> update(commentData, comment))
				.orElseGet(() -> create(commentData));
	}

	@Override
	@PreAuthorize("@securityHelper.canDeleteComment(#code, authentication)")
	public boolean delete(final String code) {
		requireNonNull(code, "The Code has a null value.");
		val comment = commentService.findById(SqidsUtil.decodeId(code));
		comment.ifPresent(commentService::delete);
		return comment.isPresent();
	}

	@Override
	public SimplePage<CommentData> findAll(SimplePageRequest pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return commentService.findAll(pageable).map(commentMapper::toData);
	}

	@Override
	public Optional<CommentData> findByCode(String code) {
		requireNonNull(code, "The Code has a null value.");
		return commentService.findById(SqidsUtil.decodeId(code)).map(commentMapper::toData);
	}

	private CommentData update(final CommentData commentData, final Comment comment) {
		commentMapper.updateModelFromData(commentData, comment);
		val updatedComment = commentService.save(comment);
		return commentMapper.toData(updatedComment);
	}

	private CommentData create(final CommentData commentData) {
		val createdComment = commentService.save(commentMapper.toModel(commentData));
		return commentMapper.toData(createdComment);
	}
}
