package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.data.CommentData;
import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.application.facade.CommentFacade;
import br.com.rafaelbiasi.blog.application.mapper.CommentMapper;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.service.CommentService;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
@Transactional
@RequiredArgsConstructor
public class CommentFacadeImpl implements CommentFacade {

	private final CommentService commentService;
	private final CommentMapper commentMapper;

	@Override
	public void save(
			final CommentData comment,
			final String postCode,
			final Principal principal
	) {
		requireNonNull(comment, "The Comment has a null value.");
		requireNonNull(postCode, "The Post Code has a null value.");
		requireNonNull(principal, "The Principal has a null value.");
		comment.setAuthor(
				UserData.builder()
						.username(principal.getName())
						.build());
		comment.setPost(PostData.builder().code(postCode).build());
		commentService.save(commentMapper.toModel(comment));
	}

	@Override
	public boolean delete(final String code) {
		requireNonNull(code, "The Code has a null value.");
		val post = commentService.findById(SqidsUtil.decodeId(code));
		post.ifPresent(commentService::delete);
		return post.isPresent();
	}

	@Override
	public Page<CommentData> findAll(Pageable pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		val map = commentService.findAll(PageRequestModel.of(
						pageable.getPageNumber(),
						pageable.getPageSize()
				))
				.map(commentMapper::toData);
		val pageRequestModel = map.getPageable();
		return new PageImpl<>(map.content(), PageRequest.of(
				pageRequestModel.pageNumber(),
				pageRequestModel.pageSize()
		), map.total());
	}

	@Override
	public Optional<CommentData> findByCode(String code) {
		requireNonNull(code, "The Code has a null value.");
		return commentService.findById(SqidsUtil.decodeId(code))
				.map(commentMapper::toData);
	}

}
