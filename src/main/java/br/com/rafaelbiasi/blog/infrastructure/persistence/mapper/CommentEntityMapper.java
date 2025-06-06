package br.com.rafaelbiasi.blog.infrastructure.persistence.mapper;

import br.com.rafaelbiasi.blog.core.model.Comment;
import br.com.rafaelbiasi.blog.infrastructure.persistence.entity.CommentEntity;
import org.mapstruct.*;

@Mapper(
		componentModel = MappingConstants.ComponentModel.SPRING,
		injectionStrategy = InjectionStrategy.SETTER,
		uses = {
				PostEntityMapper.class,
				UserEntityMapper.class
		}
)
public interface CommentEntityMapper {

	CommentEntity toEntity(Comment comment);

	@Mapping(target = "post", qualifiedByName = "PostWithoutComments")
	Comment toModel(CommentEntity commentEntity);

	@Named("CommentWithoutPost")
	@Mapping(target = "post", ignore = true)
	Comment toModelWithoutPost(CommentEntity comment);

}