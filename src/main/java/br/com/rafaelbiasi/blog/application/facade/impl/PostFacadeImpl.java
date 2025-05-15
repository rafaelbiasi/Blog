package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.facade.FileFacade;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import br.com.rafaelbiasi.blog.application.mapper.PostMapper;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.PostModel;
import br.com.rafaelbiasi.blog.core.domain.service.PostService;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {

	private final PostService postService;
	private final FileFacade fileFacade;
	private final PostMapper postMapper;

	@Override
	public List<PostData> findAll() {
		return postService.findAll().stream().map(postMapper::toDataWithoutComments).toList();
	}

	@Override
	public Page<PostData> findAll(final Pageable pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		val map = postService.findAll(PageRequestModel.of(
				pageable.getPageNumber(),
				pageable.getPageSize()
		)).map(postMapper::toDataWithoutComments);
		val pageRequestModel = map.getPageable();
		return new PageImpl<>(map.content(), PageRequest.of(
				pageRequestModel.pageNumber(),
				pageRequestModel.pageSize()
		), map.total());
	}

	public Optional<PostData> findById(final long id) {
		return postService.findById(id).map(postMapper::toData);
	}

	@Override
	public PostData save(final PostData postData) {
		requireNonNull(postData, "The Post has a null value.");
		return ofNullable(postData.getCode())
				.map(SqidsUtil::decodeId)
				.flatMap(postService::findById)
				.map(post -> update(postData, post))
				.orElse(create(postData));
	}

	@Override
	public PostData save(
			final PostData postData,
			final MultipartFile file
	) {
		requireNonNull(postData, "The Post has a null value.");
		requireNonNull(file, "The File has a null value.");
		val originalFilename = of(file)
				.map(MultipartFile::getOriginalFilename)
				.filter(not(String::isBlank));
		originalFilename.ifPresent(postData::setImageFilePath);
		val savedPostData = save(postData);
		originalFilename.ifPresent(s -> fileFacade.save(file));
		return savedPostData;
	}

	@Override
	public boolean delete(final String code) {
		requireNonNull(code, "The Code has a null value.");
		val post = postService.findById(SqidsUtil.decodeId(code));
		post.ifPresent(postService::delete);
		return post.isPresent();
	}

	@Override
	public Optional<PostData> findByCode(final String code) {
		requireNonNull(code, "The Code has a null value.");
		return postService.findById(SqidsUtil.decodeId(code))
				.map(postMapper::toData);
	}

	private PostData update(final PostData postData, final PostModel post) {
		postMapper.updateModelFromData(postData, post);
		val updatedPost = postService.save(post);
		return postMapper.toData(updatedPost);
	}

	private PostData create(final PostData postData) {
		val createdPost = postService.save(postMapper.toModel(postData));
		return postMapper.toData(createdPost);
	}
}
