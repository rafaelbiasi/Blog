package br.com.rafaelbiasi.blog.infrastructure.persistence.mapper;

import br.com.rafaelbiasi.blog.core.domain.model.CommentModel;
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

	CommentEntity toEntity(CommentModel comment);

	CommentModel toModel(CommentEntity commentEntity);

	@Named("CommentWithoutPost")
	@Mapping(target = "post", ignore = true)
	CommentModel toModelWithoutPost(CommentEntity comment);

}